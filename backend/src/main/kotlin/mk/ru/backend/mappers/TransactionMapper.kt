package mk.ru.backend.mappers

import mk.ru.backend.persistence.entities.Transaction
import mk.ru.backend.web.responses.payment.PaymentTransactionResponse
import mk.ru.backend.web.responses.wallet.WalletTransactionRecipientResponse
import mk.ru.backend.web.responses.wallet.WalletTransactionSenderResponse
import org.springframework.stereotype.Component

@Component
class TransactionMapper(
    private val productMapper: ProductMapper,
) {
    fun toWalletRecipientResponse(transaction: Transaction): WalletTransactionRecipientResponse =
        WalletTransactionRecipientResponse(
            id = transaction.id!!,
            amount = transaction.amount,
            date = transaction.date,
            senderWallet = transaction.sender.id!!,
            product = productMapper.toTransactionResponse(transaction.product),
        )

    fun toWalletSenderResponse(transaction: Transaction): WalletTransactionSenderResponse =
        WalletTransactionSenderResponse(
            id = transaction.id!!,
            amount = transaction.amount,
            feeAmount = transaction.feeAmount,
            feePercent = transaction.feePercent,
            totalAmount = transaction.amount + transaction.feeAmount,
            date = transaction.date,
            recipientWallet = transaction.recipient.id!!,
            product = productMapper.toTransactionResponse(transaction.product),
        )

    fun toPaymentResponse(transaction: Transaction): PaymentTransactionResponse =
        PaymentTransactionResponse(
            id = transaction.id!!,
            amount = transaction.amount,
            feePercent = transaction.feePercent,
            feeAmount = transaction.feeAmount
        )
}