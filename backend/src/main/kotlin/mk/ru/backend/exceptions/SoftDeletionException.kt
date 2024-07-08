package mk.ru.backend.exceptions

import org.springframework.http.HttpStatus

class SoftDeletionException(override val message: String) :
    BaseException(message = message, status = HttpStatus.NO_CONTENT)