package mk.ru.backend.services.wallet

import jakarta.transaction.Transactional
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*
import mk.ru.backend.properties.PercentsProperties
import mk.ru.backend.enums.OuterRemittanceType
import mk.ru.backend.exceptions.ContentNotFoundException
import mk.ru.backend.exceptions.SoftDeletionException
import mk.ru.backend.exceptions.ValidationException
import mk.ru.backend.mappers.WalletMapper
import mk.ru.backend.persistence.entities.AppUser
import mk.ru.backend.persistence.entities.Transaction
import mk.ru.backend.persistence.entities.Wallet
import mk.ru.backend.persistence.repositories.WalletRepo
import mk.ru.backend.services.outerremittance.OuterRemittanceService
import mk.ru.backend.utils.AppUserInfo
import mk.ru.backend.utils.CommonFunctions
import mk.ru.backend.web.responses.wallet.WalletInfoResponse
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class WalletServiceImpl(
    private val walletRepo: WalletRepo,
    private val walletMapper: WalletMapper,
    private val outerRemittanceService: OuterRemittanceService,
    private val percentsProperties: PercentsProperties
) : WalletService {
    private val log: Logger = LoggerFactory.getLogger(this.javaClass.name)

    override fun save(wallet: Wallet): Wallet = walletRepo.save(wallet)

    override fun create(owner: AppUser): Wallet {
        val savedWallet: Wallet = walletRepo.save(Wallet(owner = owner))
        log.info("Created wallet with id - ${savedWallet.id} for user - ${owner.login}")
        return savedWallet
    }

    @Transactional
    override fun getFromBalance(transaction: Transaction, wallet: Wallet) {
        AppUserInfo.checkAccessAllowed(wallet.owner?.login!!)
        val amount: BigDecimal = transaction.amount!! + transaction.feeAmount!!
        if (wallet.balance.minus(amount) <= BigDecimal.ZERO)
            throw ValidationException("Can not write off due to lack of funds")

        walletRepo.save(wallet.apply {
            balance = wallet.balance.minus(amount)
            lastModifiedDate = LocalDateTime.now()
            transactionsRecipient = wallet.transactionsRecipient!!.plus(transaction)
        })
        log.info("Got $amount from wallet - ${wallet.id}")
    }

    @Transactional
    override fun addToBalance(transaction: Transaction, wallet: Wallet) {
        val amount: BigDecimal = transaction.amount!!
        walletRepo.save(wallet.apply {
            balance = wallet.balance.plus(amount)
            lastModifiedDate = LocalDateTime.now()
            transactionsRecipient = wallet.transactionsRecipient!!.plus(transaction)
        })
        log.info("Added $amount to wallet - ${wallet.id}")
    }

    @Transactional
    override fun addBonuses() {
        val wallets: List<Wallet> =
            walletRepo.findAllByLastModifiedDateAfterAndBalanceNot(LocalDateTime.now().minusDays(1))
        wallets.forEach {
            val bonusAmount: BigDecimal = CommonFunctions.getPercent(it.balance, percentsProperties.bonus)
            outerRemittanceService.create(bonusAmount, OuterRemittanceType.BONUS, it)
            walletRepo.save(it.apply { balance = balance.plus(bonusAmount) })
        }
        log.info("Sent bonuses on ${wallets.size} wallets")
    }

    override fun findByAuthenticatedLogin(): WalletInfoResponse {
        val authenticatedLogin: String = AppUserInfo.getAuthenticatedLogin()
        return walletMapper.toInfoResponse(walletRepo.findByOwnerLogin(authenticatedLogin)
            .orElseThrow { ContentNotFoundException("Wallet for user with login - $authenticatedLogin not found") })
    }

    override fun findEntityById(id: UUID): Wallet {
        val wallet: Wallet =
            walletRepo.findById(id).orElseThrow { ContentNotFoundException("Wallet with id - $id not found") }
        if (wallet.owner!!.blocked)
            throw SoftDeletionException("Wallet with id - $id not found")
        return wallet
    }
}