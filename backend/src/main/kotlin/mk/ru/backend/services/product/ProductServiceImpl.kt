package mk.ru.backend.services.product

import jakarta.persistence.criteria.Predicate
import jakarta.transaction.Transactional
import java.util.*
import mk.ru.backend.exceptions.ContentNotFoundException
import mk.ru.backend.exceptions.SoftDeletionException
import mk.ru.backend.exceptions.ValidationException
import mk.ru.backend.mappers.ProductMapper
import mk.ru.backend.persistence.entities.AppUser
import mk.ru.backend.persistence.entities.PriceHistory
import mk.ru.backend.persistence.entities.Product
import mk.ru.backend.persistence.repositories.ProductRepo
import mk.ru.backend.services.category.CategoryService
import mk.ru.backend.services.criteria.conditions.Condition
import mk.ru.backend.services.pricehistory.PriceHistoryService
import mk.ru.backend.services.user.AppUserService
import mk.ru.backend.utils.AppUserInfo
import mk.ru.backend.utils.CommonFunctions
import mk.ru.backend.web.requests.PriceHistoryCreateRequest
import mk.ru.backend.web.requests.ProductCreateRequest
import mk.ru.backend.web.requests.ProductUpdateRequest
import mk.ru.backend.web.responses.product.ProductCreateResponse
import mk.ru.backend.web.responses.product.ProductInfoResponse
import mk.ru.backend.web.responses.product.ProductUpdateResponse
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Service

@Service
class ProductServiceImpl(
    private val productRepo: ProductRepo,
    private val appUserService: AppUserService,
    private val priceHistoryService: PriceHistoryService,
    private val productMapper: ProductMapper,
    private val categoryService: CategoryService
) : ProductService {
    private val log: Logger = LoggerFactory.getLogger(this.javaClass.name)

    override fun search(conditions: List<Condition<Any>>?, pageable: Pageable?): Page<ProductInfoResponse> =
        productRepo.findAll(
            CommonFunctions.getSpecification(conditions), pageable ?: Pageable.unpaged()
        ).map { productMapper.toInfoResponse(it) }

    override fun find(
        byOwner: Boolean?,
        showDeleted: Boolean?,
        onlySelling: Boolean?,
        pageable: Pageable?
    ): Page<ProductInfoResponse> {
        val spec: Specification<Product> = Specification<Product> { root, _, criteriaBuilder ->
            val predicates = mutableListOf<Predicate>()

            if (byOwner == true)
                predicates.add(
                    criteriaBuilder.equal(
                        root.join<Product, AppUser>("owner").get<String>("login"),
                        AppUserInfo.getAuthenticatedLogin()
                    )
                )
            if (showDeleted == false || showDeleted == null)
                predicates.add(criteriaBuilder.equal(root.get<Boolean>("deleted"), false))
            if (onlySelling == true) predicates.add(criteriaBuilder.equal(root.get<Boolean>("selling"), true))

            criteriaBuilder.and(* predicates.toTypedArray())
        }

        return productRepo.findAll(spec, pageable ?: Pageable.unpaged()).map { productMapper.toInfoResponse(it) }
    }

    override fun findById(id: UUID): ProductInfoResponse = productMapper.toInfoResponse(findEntityById(id))

    override fun create(productCreateRequest: ProductCreateRequest): ProductCreateResponse {
        val product: Product = productMapper.toEntity(productCreateRequest)

        product.owner = appUserService.getAuthenticated(blockedCheck = true)
        product.category = categoryService.findEntityById(productCreateRequest.category)

        val savedProduct: Product = productRepo.save(product)

        priceHistoryService.create(
            PriceHistoryCreateRequest(
                product = savedProduct,
                appUser = savedProduct.owner!!,
                price = productCreateRequest.price
            )
        )

        categoryService.updateCharacteristics(productCreateRequest.category)

        log.info("Created product with id - ${savedProduct.id}")
        return productMapper.toCreateResponse(savedProduct)
    }

    override fun update(productUpdateRequest: ProductUpdateRequest): ProductUpdateResponse {
        val product: Product = findEntityById(id = productUpdateRequest.id, deletionCheck = true)
        AppUserInfo.checkAccessAllowed(product.owner?.login!!)

        val updatedProduct: Product = product.apply {
            productUpdateRequest.name?.let { name = it }
            productUpdateRequest.description?.let { description = it }
            productUpdateRequest.price?.let {
                if (CommonFunctions.getActualPrice(product) != it) {
                    val productPriceHistory: PriceHistory = priceHistoryService.create(
                        PriceHistoryCreateRequest(
                            product = product,
                            appUser = appUserService.getAuthenticated(blockedCheck = true),
                            price = it
                        )
                    )
                    priceHistory = priceHistory!!.plus(productPriceHistory)
                }
            }
            productUpdateRequest.category?.let { category = categoryService.findEntityById(it) }
        }

        saveProduct(updatedProduct)

        log.info("Updated product with id - ${updatedProduct.id}")
        return productMapper.toUpdateResponse(updatedProduct)
    }

    override fun sell(id: UUID): ProductInfoResponse {
        val product: Product = findEntityById(id = id, deletionCheck = true)
        AppUserInfo.checkAccessAllowed(product.owner?.login!!)

        if (product.selling!!)
            throw SoftDeletionException("Product with id - ${product.id} is already selling")

        product.selling = true
        saveProduct(product)
        log.info("Set selling product with id - $id to 'true'")

        return productMapper.toInfoResponse(product)
    }

    override fun unsell(id: UUID): ProductInfoResponse {
        val product: Product = findEntityById(id = id, deletionCheck = true)
        AppUserInfo.checkAccessAllowed(product.owner?.login!!)

        if (!product.selling!!)
            throw SoftDeletionException("Product with id - ${product.id} is not selling")

        product.selling = false
        saveProduct(product)
        log.info("Set selling product with id - $id to 'false'")

        return productMapper.toInfoResponse(product)
    }

    override fun delete(id: UUID) {
        val product: Product = findEntityById(id = id, deletionCheck = true)
        AppUserInfo.checkAccessAllowed(product.owner?.login!!)

        product.deleted = true
        saveProduct(product)
        log.info("Deleted product with id - $id")
    }

    override fun restore(id: UUID) {
        val product: Product = findEntityById(id)
        AppUserInfo.checkAccessAllowed(product.owner?.login!!)

        when (product.deleted!!) {
            true -> product.deleted = false
            false -> throw SoftDeletionException("Product with id - $id is not deleted")
        }
        saveProduct(product)
        log.info("Restored product with id - $id")
    }

    @Transactional
    override fun transfer(product: Product, toUser: AppUser) {
        if (!product.selling!!) throw ValidationException("Product is not selling")
        if (product.owner!!.login == toUser.login)
            throw ValidationException("Product is already belongs to user with login - ${toUser.login}")
        product.owner = toUser
        product.selling = false
        saveProduct(product)
        log.info("Transferred product with id - ${product.id} to user with login - ${toUser.login}")
    }

    override fun findEntityById(id: UUID, deletionCheck: Boolean): Product {
        val product: Product =
            productRepo.findById(id).orElseThrow { ContentNotFoundException("Product with id - $id not found") }
        if (deletionCheck && product.deleted!!)
            throw SoftDeletionException("Product with id - $id not found")
        return product
    }

    private fun saveProduct(product: Product) {
        productRepo.save(product)
        categoryService.updateCharacteristics(product.category!!.name!!)
    }
}