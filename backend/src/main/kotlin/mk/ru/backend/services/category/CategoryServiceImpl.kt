package mk.ru.backend.services.category

import jakarta.transaction.Transactional
import java.math.BigDecimal
import mk.ru.backend.exceptions.ContentNotFoundException
import mk.ru.backend.persistence.entities.Category
import mk.ru.backend.persistence.repositories.CategoryRepo
import mk.ru.backend.utils.AppUserInfo
import mk.ru.backend.utils.CommonFunctions
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class CategoryServiceImpl(private val categoryRepo: CategoryRepo) : CategoryService {
    private val log: Logger = LoggerFactory.getLogger(this.javaClass.name)

    override fun create(name: String) {
        AppUserInfo.checkAccessAllowed()
        val savedCategory: Category = categoryRepo.save(Category(name = name))
        log.info("Saved new category with name - ${savedCategory.name}")
    }

    @Transactional
    override fun updateCharacteristics(name: String) {
        val category: Category = findEntityById(name)

        val prices: List<BigDecimal> = category.products!!
            .filter { it.selling!! && !it.deleted!! }
            .map { CommonFunctions.getActualPrice(it) }

        val min: BigDecimal = if (prices.isNotEmpty()) prices.min() else category.minPrice
        val max: BigDecimal = if (prices.isNotEmpty()) prices.max() else category.maxPrice
        val average: BigDecimal = CommonFunctions.findAverage(mutableListOf(min, max))

        categoryRepo.save(category.apply {
            minPrice = min
            maxPrice = max
            averagePrice = average
            minAveragePrice = CommonFunctions.findAverage(mutableListOf(min, average))
            maxAveragePrice = CommonFunctions.findAverage(mutableListOf(average, max))
        })
        log.info("Updated category with name - $name")
    }

    override fun findEntityById(name: String): Category {
        return categoryRepo.findByName(name)
            .orElseThrow { ContentNotFoundException("Category with name - $name not found") }
    }
}