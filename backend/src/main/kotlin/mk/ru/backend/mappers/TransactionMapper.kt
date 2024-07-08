package mk.ru.backend.mappers

import mk.ru.backend.persistence.entities.Transaction
import mk.ru.backend.web.responses.payment.PaymentTransactionInfoResponse
import mk.ru.backend.web.responses.wallet.WalletTransactionRecipientInfoResponse
import mk.ru.backend.web.responses.wallet.WalletTransactionSenderInfoResponse
import org.springframework.stereotype.Component

@Component
class TransactionMapper(
    private val productMapper: ProductMapper,
) {
    fun toWalletRecipientInfoResponse(transaction: Transaction): WalletTransactionRecipientInfoResponse =
        WalletTransactionRecipientInfoResponse(
            id = transaction.id!!,
            amount = transaction.amount!!,
            date = transaction.date!!,
            senderWallet = transaction.sender!!.id!!,
            product = productMapper.toTransactionInfoResponse(transaction.product!!),
        )

    fun toWalletSenderInfoResponse(transaction: Transaction): WalletTransactionSenderInfoResponse =
        WalletTransactionSenderInfoResponse(
            id = transaction.id!!,
            amount = transaction.amount!!,
            feeAmount = transaction.feeAmount!!,
            feePercent = transaction.feePercent!!,
            totalAmount = transaction.amount!! + transaction.feeAmount!!,
            date = transaction.date!!,
            recipientWallet = transaction.recipient!!.id!!,
            product = productMapper.toTransactionInfoResponse(transaction.product!!),
        )

    fun toPaymentInfoResponse(transaction: Transaction): PaymentTransactionInfoResponse =
        PaymentTransactionInfoResponse(
            id = transaction.id!!,
            amount = transaction.amount!!,
            feePercent = transaction.feePercent!!,
            feeAmount = transaction.feeAmount!!
        )
}