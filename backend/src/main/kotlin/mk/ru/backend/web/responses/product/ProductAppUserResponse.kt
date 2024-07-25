package mk.ru.backend.web.responses.product

data class ProductAppUserResponse(
    val login: String,
    val mail: String,
    val blocked: Boolean
)
