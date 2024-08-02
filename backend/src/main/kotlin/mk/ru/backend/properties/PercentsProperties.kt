package mk.ru.backend.properties

import java.math.BigDecimal
import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "app.percents")
data class PercentsProperties(
    val fee: BigDecimal,
    val bonus: BigDecimal
)
