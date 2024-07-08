package mk.ru.backend.exceptions

class ValidationException(override val message: String) : BaseException(message = message)