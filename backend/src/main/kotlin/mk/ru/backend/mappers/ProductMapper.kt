package mk.ru.backend.mappers

import mk.ru.backend.persistence.entities.Product
import mk.ru.backend.utils.CommonFunctions
import mk.ru.backend.web.requests.ProductCreateRequest
import mk.ru.backend.web.responses.payment.PaymentProductInfoResponse
import mk.ru.backend.web.responses.product.ProductCreateResponse
import mk.ru.backend.web.responses.product.ProductInfoResponse
import mk.ru.backend.web.responses.product.ProductUpdateResponse
import mk.ru.backend.web.responses.transaction.TransactionProductInfoResponse
import org.springframework.stereotype.Component

@Component
class ProductMapper(
    private val appUserMapper: AppUserMapper
) {
    fun toCreateResponse(product: Product): ProductCreateResponse = ProductCreateResponse(
        id = product.id!!,
        registrationDate = product.registrationDate!!
    )

    fun toUpdateResponse(product: Product): ProductUpdateResponse = ProductUpdateResponse(
        id = product.id!!,
        name = product.name!!,
        description = product.description!!,
        actualPrice = CommonFunctions.getActualPrice(product)
    )

    fun toInfoResponse(product: Product): ProductInfoResponse = ProductInfoResponse(
        id = product.id!!,
        name = product.name!!,
        description = product.description,
        registrationDate = product.registrationDate!!,
        deleted = product.deleted!!,
        actualPrice = CommonFunctions.getActualPrice(product),
        selling = product.selling!!,
        owner = appUserMapper.toProductInfoResponse(product.owner!!)
    )

    fun toTransactionInfoResponse(product: Product): TransactionProductInfoResponse = TransactionProductInfoResponse(
        id = product.id!!,
        name = product.name!!,
        description = product.description,
        registrationDate = product.registrationDate!!
    )

    fun toPaymentInfoResponse(product: Product): PaymentProductInfoResponse = PaymentProductInfoResponse(
        id = product.id!!,
        name = product.name!!,
        actualPrice = CommonFunctions.getActualPrice(product)
    )

    fun toEntity(product: ProductCreateRequest): Product = Product(
        name = product.name,
        description = product.description,
    )
}