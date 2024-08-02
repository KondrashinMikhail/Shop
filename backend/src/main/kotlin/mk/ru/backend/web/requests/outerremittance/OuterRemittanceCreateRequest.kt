package mk.ru.backend.web.requests.outerremittance

import java.math.BigDecimal
import java.util.*

data class OuterRemittanceCreateRequest(
    val walletId: UUID,
    val amount: BigDecimal,
)
