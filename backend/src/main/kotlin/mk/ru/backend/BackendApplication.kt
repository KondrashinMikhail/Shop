package mk.ru.backend

import mk.ru.backend.properties.JwtProperties
import mk.ru.backend.properties.MinioProperties
import mk.ru.backend.properties.PercentsProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableConfigurationProperties(PercentsProperties::class, JwtProperties::class, MinioProperties::class)
@EnableScheduling
class BackendApplication

fun main(args: Array<String>) {
    runApplication<BackendApplication>(*args)
}
