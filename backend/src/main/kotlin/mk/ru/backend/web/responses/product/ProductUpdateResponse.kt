package mk.ru.backend.web.responses.product

import java.math.BigDecimal
import java.util.*

data class ProductUpdateResponse(
    val id: UUID,
    val name: String,
    val description: String,
    val actualPrice: BigDecimal,
    val category: String
)
