package mk.ru.backend.web.controllers

import java.util.UUID
import mk.ru.backend.annotations.CommonController
import mk.ru.backend.criteria.conditions.Condition
import mk.ru.backend.services.pricehistory.PriceHistoryService
import mk.ru.backend.services.product.ProductService
import mk.ru.backend.web.requests.product.ProductCreateRequest
import mk.ru.backend.web.requests.product.ProductUpdateRequest
import mk.ru.backend.web.responses.pricehistory.PriceHistoryInfoResponse
import mk.ru.backend.web.responses.product.ProductCreateResponse
import mk.ru.backend.web.responses.product.ProductInfoResponse
import mk.ru.backend.web.responses.product.ProductUpdateResponse
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@CommonController
@RequestMapping("/product")
class ProductController(
    private val productService: ProductService,
    private val priceHistoryService: PriceHistoryService
) {
    @PostMapping("/search")
    fun search(
        @RequestBody(required = false) conditions: List<Condition<Any>>?,
        @RequestParam(required = false) pageable: Pageable?
    ): ResponseEntity<Page<ProductInfoResponse>> =
        ResponseEntity.ok(productService.search(conditions = conditions, pageable = pageable))

    @GetMapping
    fun find(
        @RequestParam(required = false) onlySelling: Boolean? = true,
        @RequestParam(required = false) byOwner: Boolean? = false,
        @RequestParam(required = false) showDeleted: Boolean? = false,
        @RequestParam(required = false) pageable: Pageable?
    ): ResponseEntity<Page<ProductInfoResponse>> =
        ResponseEntity.ok(
            productService.find(
                byOwner = byOwner,
                showDeleted = showDeleted,
                onlySelling = onlySelling,
                pageable = pageable
            )
        )

    @GetMapping("/{id}")
    fun findById(@PathVariable id: UUID): ResponseEntity<ProductInfoResponse> =
        ResponseEntity.ok(productService.findById(id))

    @PostMapping
    fun create(@RequestBody productCreateRequest: ProductCreateRequest): ResponseEntity<ProductCreateResponse> =
        ResponseEntity.ok(productService.create(productCreateRequest))

    @PatchMapping("/{id}")
    fun update(
        @PathVariable id: UUID,
        @RequestBody productUpdateRequest: ProductUpdateRequest
    ): ResponseEntity<ProductUpdateResponse> =
        ResponseEntity.ok(productService.update(id, productUpdateRequest))

    @PostMapping("/{id}/sell")
    fun sell(@PathVariable id: UUID): ResponseEntity<ProductInfoResponse> = ResponseEntity.ok(productService.sell(id))

    @PostMapping("/{id}/unsell")
    fun unsell(@PathVariable id: UUID): ResponseEntity<ProductInfoResponse> =
        ResponseEntity.ok(productService.unsell(id))

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: UUID) = productService.delete(id)

    @PatchMapping("/{id}/restore")
    fun restore(@PathVariable id: UUID) = productService.restore(id)

    @GetMapping("/{productId}/price-history")
    fun getPriceHistory(
        @PathVariable productId: UUID,
        @RequestBody(required = false) conditions: List<Condition<Any>>?,
        @RequestParam(required = false) pageable: Pageable?
    ): ResponseEntity<Page<PriceHistoryInfoResponse>> =
        ResponseEntity.ok(
            priceHistoryService.searchPriceHistory(
                productId = productId,
                conditions = conditions,
                pageable = pageable
            )
        )
}