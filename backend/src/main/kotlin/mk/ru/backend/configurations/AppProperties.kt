package mk.ru.backend.configurations

import java.math.BigDecimal
import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "app")
data class AppProperties(
    val jwt: JwtProperties,
    val feePercent: BigDecimal,
    val bonusPercent: BigDecimal
)
