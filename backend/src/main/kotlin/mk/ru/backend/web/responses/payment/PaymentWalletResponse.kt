package mk.ru.backend.web.responses.payment

import java.util.*

data class PaymentWalletResponse(
    val walletId: UUID,
    val userLogin: String
)
