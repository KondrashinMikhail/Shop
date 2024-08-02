package mk.ru.backend.web.responses.wallet

import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

data class WalletInfoResponse(
    val id: UUID,
    val balance: BigDecimal,
    val lastModifiedDate: LocalDateTime,
    val owner: WalletAppUserResponse,
    val transactionsSender: List<WalletTransactionSenderResponse>?,
    val transactionsRecipient: List<WalletTransactionRecipientResponse>?,
    val incomingOuterRemittances: List<WalletOuterRemittanceResponse>?,
    val outgoingOuterRemittances: List<WalletOuterRemittanceResponse>?,
    val bonusOuterRemittances: List<WalletOuterRemittanceResponse>?
)
