package mk.ru.backend.mappers

import mk.ru.backend.persistence.entities.Wallet
import mk.ru.backend.web.responses.payment.PaymentWalletInfoResponse
import mk.ru.backend.web.responses.wallet.WalletInfoResponse
import org.springframework.stereotype.Component

@Component
class WalletMapper(
    private val appUserMapper: AppUserMapper,
    private val transactionMapper: TransactionMapper
) {
    fun toInfoResponse(wallet: Wallet): WalletInfoResponse = WalletInfoResponse(
        id = wallet.id!!,
        balance = wallet.balance,
        lastModifiedDate = wallet.lastModifiedDate,
        owner = appUserMapper.toWalletInfoResponse(wallet.owner!!),
        transactionsSender = wallet.transactionsSender!!.map { transactionMapper.toWalletSenderInfoResponse(it) },
        transactionsRecipient = wallet.transactionsRecipient!!.map { transactionMapper.toWalletRecipientInfoResponse(it) }
    )

    fun toPaymentInfoResponse(wallet: Wallet): PaymentWalletInfoResponse = PaymentWalletInfoResponse(
        walletId = wallet.id!!,
        userLogin = wallet.owner!!.login!!
    )
}