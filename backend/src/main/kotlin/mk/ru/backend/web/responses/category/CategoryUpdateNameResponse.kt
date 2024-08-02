package mk.ru.backend.web.responses.category

data class CategoryUpdateNameResponse(
    val previousName: String,
    val updatedName: String
)
