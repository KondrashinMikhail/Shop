package mk.ru.backend.web.requests.product

import java.math.BigDecimal

data class ProductUpdateRequest(
    val name: String?,
    val description: String?,
    val price: BigDecimal?,
    val category: String?
)
