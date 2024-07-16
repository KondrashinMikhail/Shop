package mk.ru.backend.web.responses.wallet

import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.UUID

data class WalletTransactionRecipientInfoResponse(
    val id: UUID,
    val amount: BigDecimal,
    val date: LocalDateTime,
    val senderWallet: UUID,
    val product: WalletProductInfoResponse?
)
