package mk.ru.backend.web.requests

import java.math.BigDecimal
import java.util.UUID

data class ProductUpdateRequest(
    val id: UUID,
    val name: String?,
    val description: String?,
    val price: BigDecimal?,
)
