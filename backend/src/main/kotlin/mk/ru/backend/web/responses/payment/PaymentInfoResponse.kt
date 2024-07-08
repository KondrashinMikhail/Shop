package mk.ru.backend.web.responses.payment

data class PaymentInfoResponse(
    val product: PaymentProductInfoResponse,
    val sender: PaymentWalletInfoResponse,
    val recipient: PaymentWalletInfoResponse,
    val transaction: PaymentTransactionInfoResponse
)
