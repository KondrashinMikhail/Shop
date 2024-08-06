package mk.ru.backend.services.category

import mk.ru.backend.persistence.entities.Category
import mk.ru.backend.web.requests.category.CategoryCreateRequest
import mk.ru.backend.web.requests.category.CategoryUpdateNameRequest
import mk.ru.backend.web.responses.category.CategoryCreateResponse
import mk.ru.backend.web.responses.category.CategoryInfoResponse
import mk.ru.backend.web.responses.category.CategoryUpdateNameResponse

interface CategoryService {
    fun create(categoryCreateRequest: CategoryCreateRequest): CategoryCreateResponse
    fun updateName(categoryName: String, categoryUpdateNameRequest: CategoryUpdateNameRequest): CategoryUpdateNameResponse
    fun updateCharacteristics(name: String)
    fun findEntityById(name: String): Category
    fun findAll(): List<CategoryInfoResponse>
}
