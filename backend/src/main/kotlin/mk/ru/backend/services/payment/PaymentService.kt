package mk.ru.backend.services.payment

import java.util.UUID
import mk.ru.backend.web.responses.payment.PaymentInfoResponse

interface PaymentService {
    fun buyProduct(productId: UUID): PaymentInfoResponse
}