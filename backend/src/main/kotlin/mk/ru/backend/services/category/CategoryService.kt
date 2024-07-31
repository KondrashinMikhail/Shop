package mk.ru.backend.services.category

import mk.ru.backend.persistence.entities.Category

interface CategoryService {
    fun create(name: String)
    fun updateCharacteristics(name: String)
    fun findEntityById(name: String): Category
}