package mk.ru.backend.web.responses.wallet

import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.UUID

data class WalletInfoResponse(
    val id: UUID,
    val balance: BigDecimal,
    val lastModifiedDate: LocalDateTime,
    val owner: WalletAppUserInfoResponse,
    val transactionsSender: List<WalletTransactionSenderInfoResponse>,
    val transactionsRecipient: List<WalletTransactionRecipientInfoResponse>
)
