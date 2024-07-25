package mk.ru.backend.web.responses.wallet

import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

data class WalletOuterRemittanceResponse(
    val id: UUID,
    val amount: BigDecimal,
    val date: LocalDateTime,
)
