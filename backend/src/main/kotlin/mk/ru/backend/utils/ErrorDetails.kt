package mk.ru.backend.utils

import java.time.LocalDateTime

data class ErrorDetails(
    val timestamp: LocalDateTime = LocalDateTime.now(),
    val message: String,
    val exceptionClass: String,
    val exceptionProducer: String
)
