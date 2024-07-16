package mk.ru.backend.services.product

import java.util.UUID
import mk.ru.backend.persistence.entities.AppUser
import mk.ru.backend.persistence.entities.Product
import mk.ru.backend.services.criteria.conditions.Condition
import mk.ru.backend.web.requests.ProductCreateRequest
import mk.ru.backend.web.requests.ProductUpdateRequest
import mk.ru.backend.web.responses.product.ProductCreateResponse
import mk.ru.backend.web.responses.product.ProductInfoResponse
import mk.ru.backend.web.responses.product.ProductUpdateResponse
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface ProductService {
    fun search(conditions: List<Condition<Any>>?, pageable: Pageable?): Page<ProductInfoResponse>
    fun find(
        byOwner: Boolean? = false,
        showDeleted: Boolean? = false,
        onlySelling: Boolean? = true,
        pageable: Pageable?
    ): Page<ProductInfoResponse>

    fun findById(id: UUID): ProductInfoResponse
    fun create(productCreateRequest: ProductCreateRequest): ProductCreateResponse
    fun update(productUpdateRequest: ProductUpdateRequest): ProductUpdateResponse
    fun sell(id: UUID): ProductInfoResponse
    fun unsell(id: UUID): ProductInfoResponse
    fun delete(id: UUID)
    fun restore(id: UUID)
    fun findEntityById(id: UUID, deletionCheck: Boolean = false): Product
    fun transfer(product: Product, toUser: AppUser)
}