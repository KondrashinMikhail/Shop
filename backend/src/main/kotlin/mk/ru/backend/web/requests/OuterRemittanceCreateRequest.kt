package mk.ru.backend.web.requests

import java.math.BigDecimal
import java.util.*

data class OuterRemittanceCreateRequest(
    val walletId: UUID,
    val amount: BigDecimal,
)
