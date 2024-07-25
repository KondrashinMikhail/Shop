package mk.ru.backend.services.payment

import mk.ru.backend.enums.OuterRemittanceType
import mk.ru.backend.web.requests.OuterRemittanceCreateRequest
import mk.ru.backend.web.responses.payment.PaymentInfoResponse
import java.util.*

interface PaymentService {
    fun buyProduct(productId: UUID): PaymentInfoResponse
    fun doOuterRemittanceOperation(
        outerRemittanceCreateRequest: OuterRemittanceCreateRequest,
        outerRemittanceType: OuterRemittanceType
    )
}