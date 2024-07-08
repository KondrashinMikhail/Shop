package mk.ru.backend.exceptions

import org.springframework.http.HttpStatus

class AccessDeniedException(override val message: String) :
    BaseException(message = message, status = HttpStatus.FORBIDDEN)