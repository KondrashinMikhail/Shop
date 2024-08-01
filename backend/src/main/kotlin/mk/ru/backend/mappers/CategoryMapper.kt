package mk.ru.backend.mappers

import mk.ru.backend.persistence.entities.Category
import mk.ru.backend.web.responses.category.CategoryCreateResponse
import org.springframework.stereotype.Component

@Component
class CategoryMapper {
    fun toCreateResponse(category: Category): CategoryCreateResponse = CategoryCreateResponse(
        name = category.name!!
    )
}