package mk.ru.backend.persistence.repositories

import java.util.*
import mk.ru.backend.persistence.entities.Category
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CategoryRepo : JpaRepository<Category, UUID> {
    fun findByName(name: String): Optional<Category>
}