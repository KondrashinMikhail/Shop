package mk.ru.backend.web.controllers

import java.util.UUID
import mk.ru.backend.annotations.CommonController
import mk.ru.backend.enums.OuterRemittanceType
import mk.ru.backend.services.payment.PaymentService
import mk.ru.backend.web.requests.outerremittance.OuterRemittanceCreateRequest
import mk.ru.backend.web.responses.payment.PaymentInfoResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping

@CommonController
@RequestMapping("/payment")
class PaymentController(
    private val paymentService: PaymentService
) {
    @PostMapping("/product/{productId}")
    fun buyProduct(@PathVariable productId: UUID): ResponseEntity<PaymentInfoResponse> =
        ResponseEntity.ok(paymentService.buyProduct(productId))

    @PostMapping("/replenish")
    fun replenish(@RequestBody outerRemittanceCreateRequest: OuterRemittanceCreateRequest) =
        paymentService.doOuterRemittanceOperation(
            outerRemittanceCreateRequest,
            OuterRemittanceType.INCOMING
        )

    @PostMapping("/withdraw")
    fun withdraw(@RequestBody outerRemittanceCreateRequest: OuterRemittanceCreateRequest) =
        paymentService.doOuterRemittanceOperation(
            outerRemittanceCreateRequest,
            OuterRemittanceType.OUTGOING
        )
}