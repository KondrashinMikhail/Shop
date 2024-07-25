package mk.ru.backend.services.payment

import jakarta.transaction.Transactional
import mk.ru.backend.enums.OuterRemittanceType
import mk.ru.backend.exceptions.AccessDeniedException
import mk.ru.backend.exceptions.SoftDeletionException
import mk.ru.backend.exceptions.ValidationException
import mk.ru.backend.mappers.ProductMapper
import mk.ru.backend.mappers.TransactionMapper
import mk.ru.backend.mappers.WalletMapper
import mk.ru.backend.persistence.entities.AppUser
import mk.ru.backend.persistence.entities.Product
import mk.ru.backend.persistence.entities.Transaction
import mk.ru.backend.persistence.entities.Wallet
import mk.ru.backend.services.outerremittance.OuterRemittanceService
import mk.ru.backend.services.product.ProductService
import mk.ru.backend.services.transaction.TransactionService
import mk.ru.backend.services.user.AppUserService
import mk.ru.backend.services.wallet.WalletService
import mk.ru.backend.utils.AppUserInfo
import mk.ru.backend.utils.CommonFunctions
import mk.ru.backend.web.requests.OuterRemittanceCreateRequest
import mk.ru.backend.web.responses.payment.PaymentInfoResponse
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.util.*

@Service
class PaymentServiceImpl(
    private val walletService: WalletService,
    private val outerRemittanceService: OuterRemittanceService,
    private val transactionService: TransactionService,
    private val productService: ProductService,
    private val appUserService: AppUserService,
    private val walletMapper: WalletMapper,
    private val productMapper: ProductMapper,
    private val transactionMapper: TransactionMapper,
) : PaymentService {
    private val log: Logger = LoggerFactory.getLogger(this.javaClass.name)

    @Value("\${app.fee-percent}")
    private val feePercent: BigDecimal = BigDecimal.ZERO

    @Transactional
    override fun doOuterRemittanceOperation(
        outerRemittanceCreateRequest: OuterRemittanceCreateRequest,
        outerRemittanceType: OuterRemittanceType
    ) {
        val wallet: Wallet = walletService.findEntityById(outerRemittanceCreateRequest.walletId)
        AppUserInfo.checkAccessAllowed(wallet.owner!!.login)

        if (outerRemittanceType == OuterRemittanceType.OUTGOING) {
            if (wallet.balance.minus(outerRemittanceCreateRequest.amount) < BigDecimal.ZERO)
                throw ValidationException("There are not enough funds in wallet")
            else wallet.balance = wallet.balance.minus(outerRemittanceCreateRequest.amount)
        } else if (outerRemittanceType == OuterRemittanceType.INCOMING)
            wallet.balance = wallet.balance.plus(outerRemittanceCreateRequest.amount)

        wallet.outerRemittances = wallet.outerRemittances!!.plus(
            outerRemittanceService.create(
                outerRemittanceCreateRequest.amount,
                outerRemittanceType,
                wallet
            )
        )

        walletService.save(wallet)
    }

    @Transactional
    override fun buyProduct(productId: UUID): PaymentInfoResponse {
        val product: Product = productService.findEntityById(id = productId, deletionCheck = true)
        val productOwner: AppUser = product.owner!!
        val recipientWallet: Wallet = productOwner.wallet!!
        val authenticatedUser: AppUser = appUserService.getAuthenticated(blockedCheck = true)
        val senderWallet: Wallet = authenticatedUser.wallet!!

        val productPrice = CommonFunctions.getActualPrice(product)
        val feeAmount: BigDecimal = productPrice * feePercent.divide(BigDecimal(100))

        if (productOwner.login == authenticatedUser.login)
            throw AccessDeniedException("Authenticated user is already owner of this product")
        if (productOwner.blocked)
            throw SoftDeletionException("Owner of this product is blocked")
        if (!product.selling!!)
            throw ValidationException("Product is not selling")

        val savedTransaction: Transaction = transactionService.create(
            senderWallet = senderWallet,
            recipientWallet = recipientWallet,
            amount = productPrice,
            feeAmount = feeAmount,
            feePercent = feePercent,
            product = product
        )

        walletService.getFromBalance(transaction = savedTransaction, wallet = senderWallet)
        walletService.addToBalance(transaction = savedTransaction, wallet = recipientWallet)

        productService.transfer(product = product, toUser = authenticatedUser)

        log.info(
            "Payed ${productPrice + feeAmount} for product with id - ${product.id}; " +
                    "From wallet with id - ${senderWallet.id} to wallet with id - ${recipientWallet.id};" +
                    "Fee amount is $feeAmount due to fee percent is $feePercent"
        )

        log.info("App collected commission in the amount of $feeAmount")

        return PaymentInfoResponse(
            product = productMapper.toPaymentResponse(product),
            sender = walletMapper.toPaymentResponse(senderWallet),
            recipient = walletMapper.toPaymentResponse(recipientWallet),
            transaction = transactionMapper.toPaymentResponse(savedTransaction)
        )
    }
}
