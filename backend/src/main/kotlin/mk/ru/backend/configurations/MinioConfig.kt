package mk.ru.backend.configurations

import io.minio.MinioClient
import mk.ru.backend.properties.MinioProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class MinioConfig {
    @Bean
    fun minioClient(minioProperties: MinioProperties): MinioClient {
        return MinioClient.builder()
            .endpoint(minioProperties.endpoint)
            .credentials(minioProperties.accessKey, minioProperties.secretKey)
            .build()
    }
}