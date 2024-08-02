package mk.ru.backend.services.filestorage

import java.io.OutputStream
import java.util.*
import org.springframework.web.multipart.MultipartFile

interface FilestorageService {
    fun uploadFile(productId: UUID, file: MultipartFile)
    fun downloadFiles(productId: UUID, outputStream: OutputStream)
}