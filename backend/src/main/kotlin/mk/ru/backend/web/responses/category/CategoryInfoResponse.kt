package mk.ru.backend.web.responses.category

import java.math.BigDecimal

data class CategoryInfoResponse(
    val name: String,
    var minPrice: BigDecimal,
    var maxPrice: BigDecimal,
    var averagePrice: BigDecimal,
    var minAveragePrice: BigDecimal,
    var maxAveragePrice: BigDecimal
)
