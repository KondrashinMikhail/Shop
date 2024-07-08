package mk.ru.backend.web.responses.product

data class ProductAppUserInfoResponse(
    val login: String,
    val mail: String,
    val blocked: Boolean
)
