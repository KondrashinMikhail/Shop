package mk.ru.backend.web.responses.user

data class AuthenticationResponse(
    val accessToken: String,
    val refreshToken: String
)
