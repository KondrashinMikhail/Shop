package mk.ru.backend.exceptions

import org.springframework.http.HttpStatus

open class BaseException(
    override val message: String,
    val status: HttpStatus = HttpStatus.INTERNAL_SERVER_ERROR
) : RuntimeException(message)