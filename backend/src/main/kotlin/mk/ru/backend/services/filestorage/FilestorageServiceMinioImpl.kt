package mk.ru.backend.services.filestorage

import io.minio.BucketExistsArgs
import io.minio.GetObjectArgs
import io.minio.ListObjectsArgs
import io.minio.MakeBucketArgs
import io.minio.MinioClient
import io.minio.PutObjectArgs
import jakarta.annotation.PostConstruct
import java.io.OutputStream
import java.util.UUID
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream
import mk.ru.backend.configurations.properties.MinioProperties
import mk.ru.backend.exceptions.ValidationException
import mk.ru.backend.services.product.ProductService
import org.apache.commons.io.IOUtils
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class FilestorageServiceMinioImpl(
    private val minioProperties: MinioProperties,
    private val minioClient: MinioClient,
    private val productService: ProductService
) : FilestorageService {
    private val metadataFilenameKey: String = "filename"
    private val log: Logger = LoggerFactory.getLogger(this.javaClass.name)

    @PostConstruct
    private fun initDefaultBucket() = createBucket(minioProperties.productsBucketName)

    private fun createBucket(bucketName: String?) {
        val isExists = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build())
        if (!isExists) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build())
            log.info("Created bucket - $bucketName")
        } else log.info("Bucket '$bucketName' already exists")
    }

    override fun uploadFile(productId: UUID, file: MultipartFile) {
        val filename: String = file.originalFilename!!

        if (!file.contentType!!.contains("image")) throw ValidationException("File is not a photo")

        productService.checkProductExists(productId)

        val content = file.bytes
        val metadata: Map<String, String> = mapOf(metadataFilenameKey to filename)

        minioClient.putObject(
            PutObjectArgs.builder()
                .userMetadata(metadata)
                .bucket(minioProperties.productsBucketName)
                .`object`("$productId/$filename")
                .stream(content.inputStream(), content.size.toLong(), -1)
                .build()
        )
        log.info("Uploaded file - '$filename'")
    }

    override fun downloadFiles(productId: UUID, outputStream: OutputStream) {
        productService.checkProductExists(productId)

        val productFiles = minioClient.listObjects(
            ListObjectsArgs.builder()
                .bucket(minioProperties.productsBucketName)
                .prefix(productId.toString())
                .recursive(true)
                .build()
        )

        ZipOutputStream(outputStream).use { zip ->
            productFiles.forEach { file ->
                zip.putNextEntry(ZipEntry(file.get().objectName()))
                zip.write(
                    IOUtils.toByteArray(
                        minioClient.getObject(
                            GetObjectArgs.builder()
                                .bucket(minioProperties.productsBucketName)
                                .`object`(file.get().objectName())
                                .build()
                        )
                    )
                )
            }
        }
    }
}