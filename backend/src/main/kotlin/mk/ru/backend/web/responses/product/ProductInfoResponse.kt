package mk.ru.backend.web.responses.product

import java.math.BigDecimal
import java.time.LocalDate
import java.util.*
import mk.ru.backend.enums.PriceLevel

data class ProductInfoResponse(
    val id: UUID,
    val name: String,
    val description: String?,
    val registrationDate: LocalDate,
    val deleted: Boolean,
    val actualPrice: BigDecimal,
    val feeAmount: BigDecimal,
    val totalPrice: BigDecimal,
    val selling: Boolean,
    val owner: String,
    val category: String,
    val priceLevel: PriceLevel
)
