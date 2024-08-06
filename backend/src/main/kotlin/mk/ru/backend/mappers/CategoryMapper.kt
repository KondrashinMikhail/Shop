package mk.ru.backend.mappers

import mk.ru.backend.persistence.entities.Category
import mk.ru.backend.web.responses.category.CategoryCreateResponse
import mk.ru.backend.web.responses.category.CategoryInfoResponse
import org.springframework.stereotype.Component

@Component
class CategoryMapper {
    fun toCreateResponse(category: Category): CategoryCreateResponse = CategoryCreateResponse(
        name = category.name
    )

    fun toInfoResponse(category: Category): CategoryInfoResponse = CategoryInfoResponse(
        name = category.name,
        minPrice = category.minPrice,
        maxPrice = category.maxPrice,
        averagePrice = category.averagePrice,
        minAveragePrice = category.minAveragePrice,
        maxAveragePrice = category.maxAveragePrice
    )
}