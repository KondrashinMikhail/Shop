package mk.ru.backend.web.responses.payment

data class PaymentInfoResponse(
    val product: PaymentProductResponse,
    val sender: PaymentWalletResponse,
    val recipient: PaymentWalletResponse,
    val transaction: PaymentTransactionResponse
)
