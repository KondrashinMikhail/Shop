package mk.ru.backend.web.requests.category

data class CategoryUpdateNameRequest (
    val oldName: String,
    val newName: String
)