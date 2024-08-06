package mk.ru.backend.services.outerremittance

import jakarta.transaction.Transactional
import java.math.BigDecimal
import java.time.LocalDateTime
import mk.ru.backend.enums.OuterRemittanceType
import mk.ru.backend.persistence.entities.OuterRemittance
import mk.ru.backend.persistence.entities.Wallet
import mk.ru.backend.persistence.repositories.OuterRemittanceRepo
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class OuterRemittanceServiceImpl(
    private val outerRemittanceRepo: OuterRemittanceRepo
) : OuterRemittanceService {
    private val log: Logger = LoggerFactory.getLogger(this.javaClass.name)

    @Transactional
    override fun create(amount: BigDecimal, type: OuterRemittanceType, wallet: Wallet): OuterRemittance {
        val saved: OuterRemittance = outerRemittanceRepo.save(
            OuterRemittance(
                amount = amount,
                date = LocalDateTime.now(),
                wallet = wallet,
                type = type
            )
        )
        log.info("Registered outer remittance with id - ${saved.id} for wallet - ${wallet.id}")
        return saved
    }
}