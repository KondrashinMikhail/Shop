package mk.ru.backend.services.transaction

import jakarta.transaction.Transactional
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.UUID
import mk.ru.backend.exceptions.ContentNotFoundException
import mk.ru.backend.persistence.entities.Product
import mk.ru.backend.persistence.entities.Transaction
import mk.ru.backend.persistence.entities.Wallet
import mk.ru.backend.persistence.repositories.TransactionRepo
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class TransactionServiceImpl(
    private val transactionRepo: TransactionRepo,
) : TransactionService {
    private val log: Logger = LoggerFactory.getLogger(this.javaClass.name)

    @Transactional
    override fun create(
        senderWallet: Wallet,
        recipientWallet: Wallet,
        amount: BigDecimal,
        feeAmount: BigDecimal,
        feePercent: BigDecimal,
        product: Product
    ): Transaction {
        val savedTransaction: Transaction = transactionRepo.save(
            Transaction(
                sender = senderWallet,
                recipient = recipientWallet,
                amount = amount,
                feeAmount = feeAmount,
                feePercent = feePercent,
                product = product,
                date = LocalDateTime.now()
            )
        )
        log.info("Saved transaction with id - ${savedTransaction.id}")

        return savedTransaction
    }

    override fun findEntityById(id: UUID): Transaction = transactionRepo.findById(id)
        .orElseThrow { ContentNotFoundException("Transaction with id - $id not found") }
}