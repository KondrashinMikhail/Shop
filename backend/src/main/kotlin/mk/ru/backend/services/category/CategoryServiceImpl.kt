package mk.ru.backend.services.category

import jakarta.transaction.Transactional
import java.math.BigDecimal
import mk.ru.backend.exceptions.ContentNotFoundException
import mk.ru.backend.exceptions.ValidationException
import mk.ru.backend.mappers.CategoryMapper
import mk.ru.backend.persistence.entities.Category
import mk.ru.backend.persistence.repositories.CategoryRepo
import mk.ru.backend.utils.AppUserInfo
import mk.ru.backend.utils.ExtensionFunctions
import mk.ru.backend.utils.ExtensionFunctions.getActualPrice
import mk.ru.backend.web.requests.category.CategoryCreateRequest
import mk.ru.backend.web.requests.category.CategoryUpdateNameRequest
import mk.ru.backend.web.responses.category.CategoryCreateResponse
import mk.ru.backend.web.responses.category.CategoryInfoResponse
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

    @Transactional
    override fun create(categoryCreateRequest: CategoryCreateRequest): CategoryCreateResponse {
        AppUserInfo.checkAccessAllowed()
        if (categoryCreateRequest.name.isBlank() || categoryCreateRequest.name.isEmpty())
            throw ValidationException("Can not create category with empty name")
        val savedCategory: Category = categoryRepo.save(Category(name = categoryCreateRequest.name))
        log.info("Saved new category with name - ${savedCategory.name}")
        return categoryMapper.toCreateResponse(savedCategory)
    }

    override fun updateName(
        categoryName: String,
        categoryUpdateNameRequest: CategoryUpdateNameRequest
    ): CategoryUpdateNameResponse {
        AppUserInfo.checkAccessAllowed()
        val category: Category = findEntityById(categoryName)
        categoryRepo.save(category.apply { name = categoryUpdateNameRequest.name })
        log.info("Updated category name from $categoryName to ${category.name}")
        return CategoryUpdateNameResponse(
            previousName = categoryName,
            updatedName = categoryUpdateNameRequest.name
        )
    }

    @Transactional
    override fun updateCharacteristics(name: String) {
        val category: Category = findEntityById(name)
        val prices: List<BigDecimal>? = category.products
            ?.asSequence()
            ?.filter { it.selling && !it.deleted }
            ?.map { it.getActualPrice() }
            ?.toList()

        val min: BigDecimal? = prices?.min()
        val max: BigDecimal? = prices?.max()
        val average: BigDecimal? = ExtensionFunctions.getAverage(min, max)

        categoryRepo.save(category.apply {
            min?.let { minPrice = it }
            max?.let { minPrice = it }
            average?.let { averagePrice = it }
            ExtensionFunctions.getAverage(min, average)?.let { minAveragePrice = it }
            ExtensionFunctions.getAverage(average, max)?.let { maxAveragePrice = it }
        })
        log.info("Updated category with name - $name")
    }

    override fun findEntityById(name: String): Category = categoryRepo.findByName(name)
        .orElseThrow { ContentNotFoundException("Category with name - $name not found") }

    override fun findAll(): List<CategoryInfoResponse> =
        categoryRepo.findAll().map { categoryMapper.toInfoResponse(it) }
}