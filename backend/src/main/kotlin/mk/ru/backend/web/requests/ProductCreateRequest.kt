package mk.ru.backend.web.requests

import java.math.BigDecimal

data class ProductCreateRequest(
    val name: String,
    val description: String?,
    val price: BigDecimal,
    val category: String
)