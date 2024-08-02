package mk.ru.backend.web.requests.product

import java.math.BigDecimal
import java.util.*

data class ProductUpdateRequest(
    val id: UUID,
    val name: String?,
    val description: String?,
    val price: BigDecimal?,
    val category: String?
)
