package mk.ru.backend.web.responses.wallet

import java.time.LocalDate
import java.util.UUID

data class WalletProductResponse(
    val id: UUID,
    val name: String,
    val description: String?,
    val registrationDate: LocalDate,
)
