package mk.ru.backend.web.controllers

import io.swagger.v3.oas.annotations.security.SecurityRequirement
import java.io.OutputStream
import java.util.*
import mk.ru.backend.services.filestorage.FilestorageService
import mk.ru.backend.utils.SwaggerUtils
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody

@RestController
@RequestMapping("/filestorage")
@SecurityRequirement(name = SwaggerUtils.SECURITY_SCHEME_NAME)
class FilestorageController(private val filestorageService: FilestorageService) {
    @PostMapping("/{productId}/upload")
    fun upload(@PathVariable productId: UUID, @RequestParam file: MultipartFile) =
        filestorageService.uploadFile(productId = productId, file = file)

    @GetMapping("/{productId}/download")
    fun download(@PathVariable productId: UUID): ResponseEntity<StreamingResponseBody> = ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=$productId.zip")
        .body(StreamingResponseBody { outputStream: OutputStream ->
            filestorageService.downloadFiles(
                productId = productId,
                outputStream = outputStream
            )
        })
}