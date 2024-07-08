package mk.ru.backend.web.responses.payment

import java.math.BigDecimal
import java.util.UUID

data class PaymentTransactionInfoResponse(
    val id: UUID,
    val amount: BigDecimal,
    val feePercent: BigDecimal,
    val feeAmount: BigDecimal,
)