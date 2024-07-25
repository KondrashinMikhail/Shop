package mk.ru.backend.web.responses.wallet

import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.UUID

data class WalletTransactionRecipientResponse(
    val id: UUID,
    val amount: BigDecimal,
    val date: LocalDateTime,
    val senderWallet: UUID,
    val product: WalletProductResponse?
)
