package mk.ru.backend.web.responses.transaction

import java.time.LocalDate
import java.util.UUID

data class TransactionProductInfoResponse(
    val id: UUID,
    val name: String,
    val description: String?,
    val registrationDate: LocalDate,
)
