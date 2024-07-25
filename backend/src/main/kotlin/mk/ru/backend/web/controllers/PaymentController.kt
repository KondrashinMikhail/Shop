package mk.ru.backend.web.controllers

import io.swagger.v3.oas.annotations.security.SecurityRequirement
import mk.ru.backend.enums.OuterRemittanceType
import mk.ru.backend.services.payment.PaymentService
import mk.ru.backend.utils.SwaggerUtils
import mk.ru.backend.web.requests.OuterRemittanceCreateRequest
import mk.ru.backend.web.responses.payment.PaymentInfoResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/payment")
@SecurityRequirement(name = SwaggerUtils.SECURITY_SCHEME_NAME)
class PaymentController(
    private val paymentService: PaymentService
) {
    @PostMapping("/product/{productId}")
    fun buyProduct(@PathVariable productId: UUID): ResponseEntity<PaymentInfoResponse> =
        ResponseEntity.ok(paymentService.buyProduct(productId))

    @PostMapping("/replenish")
    fun replenish(@RequestBody outerRemittanceCreateRequest: OuterRemittanceCreateRequest) =
        ResponseEntity.ok(
            paymentService.doOuterRemittanceOperation(
                outerRemittanceCreateRequest,
                OuterRemittanceType.INCOMING
            )
        )

    @PostMapping("/withdraw")
    fun withdraw(@RequestBody outerRemittanceCreateRequest: OuterRemittanceCreateRequest): ResponseEntity<Unit> =
        ResponseEntity.ok(
            paymentService.doOuterRemittanceOperation(
                outerRemittanceCreateRequest,
                OuterRemittanceType.OUTGOING
            )
        )
}