package mk.ru.backend.web.responses.payment

import java.math.BigDecimal
import java.util.UUID

data class PaymentProductInfoResponse(
    val id: UUID,
    val name: String,
    val actualPrice: BigDecimal
)
