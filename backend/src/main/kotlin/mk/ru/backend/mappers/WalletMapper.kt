package mk.ru.backend.mappers

import mk.ru.backend.enums.OuterRemittanceType
import mk.ru.backend.persistence.entities.Wallet
import mk.ru.backend.web.responses.payment.PaymentWalletResponse
import mk.ru.backend.web.responses.wallet.WalletInfoResponse
import mk.ru.backend.web.responses.wallet.WalletOuterRemittanceResponse
import org.springframework.stereotype.Component

@Component
class WalletMapper(
    private val appUserMapper: AppUserMapper,
    private val transactionMapper: TransactionMapper,
    private val outerRemittanceMapper: OuterRemittanceMapper
) {
    fun toInfoResponse(wallet: Wallet): WalletInfoResponse {
        val groupedRemittances: Map<OuterRemittanceType, List<WalletOuterRemittanceResponse>>? =
            wallet.outerRemittances?.let { allRemittances ->
                allRemittances
                    .groupBy { it.type }
                    .mapValues { it.value.map { remittance -> outerRemittanceMapper.toWalletResponse(remittance) } }
            }

        return WalletInfoResponse(
            id = wallet.id!!,
            balance = wallet.balance,
            lastModifiedDate = wallet.lastModifiedDate,
            owner = appUserMapper.toWalletResponse(wallet.owner),
            transactionsSender = wallet.transactionsSender!!.map { transactionMapper.toWalletSenderResponse(it) },
            transactionsRecipient = wallet.transactionsRecipient!!.map { transactionMapper.toWalletRecipientResponse(it) },
            incomingOuterRemittances = groupedRemittances?.let { it[OuterRemittanceType.INCOMING] },
            outgoingOuterRemittances = groupedRemittances?.let { it[OuterRemittanceType.OUTGOING] },
            bonusOuterRemittances = groupedRemittances?.let { it[OuterRemittanceType.BONUS] },
        )
    }

    fun toPaymentResponse(wallet: Wallet): PaymentWalletResponse = PaymentWalletResponse(
        walletId = wallet.id!!,
        userLogin = wallet.owner.login
    )
}