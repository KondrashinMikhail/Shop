package mk.ru.backend.web.responses.product

import java.math.BigDecimal
import java.time.LocalDate
import java.util.UUID

data class ProductInfoResponse(
    val id: UUID,
    val name: String,
    val description: String?,
    val registrationDate: LocalDate,
    val deleted: Boolean,
    val actualPrice: BigDecimal,
    val selling: Boolean,
    val owner: ProductAppUserResponse
)
