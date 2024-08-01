package mk.ru.backend.mappers

import java.math.BigDecimal
import mk.ru.backend.configurations.PercentsProperties
import mk.ru.backend.persistence.entities.Product
import mk.ru.backend.utils.CommonFunctions
import mk.ru.backend.web.requests.ProductCreateRequest
import mk.ru.backend.web.responses.payment.PaymentProductResponse
import mk.ru.backend.web.responses.product.ProductCreateResponse
import mk.ru.backend.web.responses.product.ProductInfoResponse
import mk.ru.backend.web.responses.product.ProductUpdateResponse
import mk.ru.backend.web.responses.wallet.WalletProductResponse
import org.springframework.stereotype.Component

@Component
class ProductMapper(
    private val percentsProperties: PercentsProperties
) {
    fun toCreateResponse(product: Product): ProductCreateResponse = ProductCreateResponse(
        id = product.id!!,
        registrationDate = product.registrationDate!!
    )

    fun toUpdateResponse(product: Product): ProductUpdateResponse = ProductUpdateResponse(
        id = product.id!!,
        name = product.name!!,
        description = product.description!!,
        actualPrice = CommonFunctions.getActualPrice(product),
        category = product.category!!.name!!
    )

    fun toInfoResponse(product: Product): ProductInfoResponse {
        val actualPrice: BigDecimal = CommonFunctions.getActualPrice(product)
        val feeAmount: BigDecimal = CommonFunctions.getPercent(actualPrice, percentsProperties.fee)
        return ProductInfoResponse(
            id = product.id!!,
            name = product.name!!,
            description = product.description,
            registrationDate = product.registrationDate!!,
            deleted = product.deleted!!,
            actualPrice = actualPrice,
            feeAmount = feeAmount,
            totalPrice = actualPrice + feeAmount,
            selling = product.selling!!,
            owner = product.owner!!.login!!,
            category = product.category!!.name!!,
            priceLevel = CommonFunctions.getPriceLevel(product)
        )
    }

    fun toTransactionResponse(product: Product): WalletProductResponse = WalletProductResponse(
        id = product.id!!,
        name = product.name!!,
        description = product.description,
        registrationDate = product.registrationDate!!
    )

    fun toPaymentResponse(product: Product): PaymentProductResponse = PaymentProductResponse(
        id = product.id!!,
        name = product.name!!,
        actualPrice = CommonFunctions.getActualPrice(product)
    )

    fun toEntity(product: ProductCreateRequest): Product = Product(
        name = product.name,
        description = product.description,
    )
}