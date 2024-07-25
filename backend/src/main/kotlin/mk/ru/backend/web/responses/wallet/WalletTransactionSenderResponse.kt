package mk.ru.backend.web.responses.wallet

import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.UUID

data class WalletTransactionSenderResponse(
    val id: UUID,
    val feePercent: BigDecimal,
    val feeAmount: BigDecimal,
    val amount: BigDecimal,
    val date: LocalDateTime,
    val totalAmount: BigDecimal,
    val recipientWallet: UUID,
    val product: WalletProductResponse?
)
