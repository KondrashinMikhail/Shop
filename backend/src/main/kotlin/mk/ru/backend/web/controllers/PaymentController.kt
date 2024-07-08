package mk.ru.backend.web.controllers

import io.swagger.v3.oas.annotations.security.SecurityRequirement
import java.util.UUID
import mk.ru.backend.services.payment.PaymentService
import mk.ru.backend.utils.SwaggerUtils
import mk.ru.backend.web.responses.payment.PaymentInfoResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/payment")
@SecurityRequirement(name = SwaggerUtils.SECURITY_SCHEME_NAME)
class PaymentController(private val paymentService: PaymentService) {
    @PostMapping("/product/{productId}")
    fun buyProduct(@PathVariable productId: UUID): ResponseEntity<PaymentInfoResponse> =
        ResponseEntity.ok(paymentService.buyProduct(productId))
}