package mk.ru.backend.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "minio")
data class MinioProperties(
    val endpoint: String,
    val accessKey: String,
    val secretKey: String,
    val productsBucketName: String
)
