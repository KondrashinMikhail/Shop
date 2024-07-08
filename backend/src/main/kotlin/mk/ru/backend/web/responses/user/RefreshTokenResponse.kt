package mk.ru.backend.web.responses.user

data class RefreshTokenResponse(
    val accessToken: String,
    val refreshToken: String
)