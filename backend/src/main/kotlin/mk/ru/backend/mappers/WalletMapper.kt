package mk.ru.backend.mappers

import mk.ru.backend.enums.OuterRemittanceType
import mk.ru.backend.persistence.entities.Wallet
import mk.ru.backend.web.responses.payment.PaymentWalletResponse
import mk.ru.backend.web.responses.wallet.WalletInfoResponse
import org.springframework.stereotype.Component

@Component
class WalletMapper(
    private val appUserMapper: AppUserMapper,
    private val transactionMapper: TransactionMapper,
    private val outerRemittanceMapper: OuterRemittanceMapper
) {
    fun toInfoResponse(wallet: Wallet): WalletInfoResponse = WalletInfoResponse(
        id = wallet.id!!,
        balance = wallet.balance,
        lastModifiedDate = wallet.lastModifiedDate,
        owner = appUserMapper.toWalletResponse(wallet.owner!!),
        transactionsSender = wallet.transactionsSender!!.map { transactionMapper.toWalletSenderResponse(it) },
        transactionsRecipient = wallet.transactionsRecipient!!.map { transactionMapper.toWalletRecipientResponse(it) },
        incomingOuterRemittances = wallet.outerRemittances?.let { incoming ->
            incoming.filter { it.type == OuterRemittanceType.INCOMING }
                .map { outerRemittanceMapper.toWalletResponse(it) }
        },
        outgoingOuterRemittances = wallet.outerRemittances?.let { outgoing ->
            outgoing.filter { it.type == OuterRemittanceType.OUTGOING }
                .map { outerRemittanceMapper.toWalletResponse(it) }
        },
        bonusOuterRemittances = wallet.outerRemittances?.let { bonuses ->
            bonuses.filter { it.type == OuterRemittanceType.BONUS }
                .map { outerRemittanceMapper.toWalletResponse(it) }
        },
    )

    fun toPaymentResponse(wallet: Wallet): PaymentWalletResponse = PaymentWalletResponse(
        walletId = wallet.id!!,
        userLogin = wallet.owner!!.login!!
    )
}