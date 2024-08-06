package mk.ru.backend.mappers

import java.math.BigDecimal
import mk.ru.backend.configurations.properties.PercentsProperties
import mk.ru.backend.persistence.entities.Product
import mk.ru.backend.utils.ExtensionFunctions.getActualPrice
import mk.ru.backend.utils.ExtensionFunctions.getPercent
import mk.ru.backend.utils.ExtensionFunctions.getPriceLevel
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
        registrationDate = product.registrationDate
    )

    fun toUpdateResponse(product: Product): ProductUpdateResponse = ProductUpdateResponse(
        id = product.id!!,
        name = product.name,
        description = product.description,
        actualPrice = product.getActualPrice(),
        category = product.category.name
    )

    fun toInfoResponse(product: Product): ProductInfoResponse {
        val actualPrice: BigDecimal = product.getActualPrice()
        val feeAmount: BigDecimal = actualPrice.getPercent(percentsProperties.fee)
        return ProductInfoResponse(
            id = product.id!!,
            name = product.name,
            description = product.description,
            registrationDate = product.registrationDate,
            deleted = product.deleted,
            actualPrice = actualPrice,
            feeAmount = feeAmount,
            totalPrice = actualPrice + feeAmount,
            selling = product.selling,
            owner = product.owner.login,
            category = product.category.name,
            priceLevel = product.getPriceLevel()
        )
    }

    fun toTransactionResponse(product: Product): WalletProductResponse = WalletProductResponse(
        id = product.id!!,
        name = product.name,
        description = product.description,
        registrationDate = product.registrationDate
    )

    fun toPaymentResponse(product: Product): PaymentProductResponse = PaymentProductResponse(
        id = product.id!!,
        name = product.name,
        actualPrice = product.getActualPrice()
    )
}