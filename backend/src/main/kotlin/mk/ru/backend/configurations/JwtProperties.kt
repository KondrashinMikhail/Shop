package mk.ru.backend.configurations

data class JwtProperties(
    val prefix: String,
    val header: String,
    val secret: String,
    val accessTokenExpiration: Long,
    val refreshTokenExpiration: Long
)
