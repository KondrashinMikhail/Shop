package mk.ru.backend.services.category

import jakarta.transaction.Transactional
import java.math.BigDecimal
import mk.ru.backend.exceptions.ContentNotFoundException
import mk.ru.backend.exceptions.ValidationException
import mk.ru.backend.mappers.CategoryMapper
import mk.ru.backend.persistence.entities.Category
import mk.ru.backend.persistence.repositories.CategoryRepo
import mk.ru.backend.utils.AppUserInfo
import mk.ru.backend.utils.CommonFunctions
import mk.ru.backend.web.requests.CategoryCreateRequest
import mk.ru.backend.web.requests.CategoryUpdateNameRequest
import mk.ru.backend.web.responses.category.CategoryCreateResponse
import mk.ru.backend.web.responses.category.CategoryUpdateNameResponse
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class CategoryServiceImpl(
    private val categoryRepo: CategoryRepo,
    private val categoryMapper: CategoryMapper
) : CategoryService {
    private val log: Logger = LoggerFactory.getLogger(this.javaClass.name)

    override fun create(categoryCreateRequest: CategoryCreateRequest): CategoryCreateResponse {
        AppUserInfo.checkAccessAllowed()
        if (categoryCreateRequest.name.isBlank() || categoryCreateRequest.name.isEmpty())
            throw ValidationException("Can not create category with empty name")
        val savedCategory: Category = categoryRepo.save(Category(name = categoryCreateRequest.name))
        log.info("Saved new category with name - ${savedCategory.name}")
        return categoryMapper.toCreateResponse(savedCategory)
    }

    override fun updateName(categoryUpdateNameRequest: CategoryUpdateNameRequest): CategoryUpdateNameResponse {
        AppUserInfo.checkAccessAllowed()
        val category: Category = findEntityById(categoryUpdateNameRequest.oldName)
        categoryRepo.save(category.apply { name = categoryUpdateNameRequest.newName })
        log.info("Updated category name from ${categoryUpdateNameRequest.oldName} to ${category.name}")
        return CategoryUpdateNameResponse(
            previousName = categoryUpdateNameRequest.oldName,
            updatedName = categoryUpdateNameRequest.newName
        )
    }

    @Transactional
    override fun updateCharacteristics(name: String) {
        val category: Category = findEntityById(name)

        val prices: List<BigDecimal> = category.products!!
            .filter { it.selling!! && !it.deleted!! }
            .map { CommonFunctions.getActualPrice(it) }

        val min: BigDecimal = if (prices.isNotEmpty()) prices.min() else category.minPrice
        val max: BigDecimal = if (prices.isNotEmpty()) prices.max() else category.maxPrice
        val average: BigDecimal = CommonFunctions.getAverage(mutableListOf(min, max))

        categoryRepo.save(category.apply {
            minPrice = min
            maxPrice = max
            averagePrice = average
            minAveragePrice = CommonFunctions.getAverage(mutableListOf(min, average))
            maxAveragePrice = CommonFunctions.getAverage(mutableListOf(average, max))
        })
        log.info("Updated category with name - $name")
    }

    override fun findEntityById(name: String): Category = categoryRepo.findByName(name)
        .orElseThrow { ContentNotFoundException("Category with name - $name not found") }
}