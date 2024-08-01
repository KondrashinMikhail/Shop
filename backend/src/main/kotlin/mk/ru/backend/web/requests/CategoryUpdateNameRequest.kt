package mk.ru.backend.web.requests

data class CategoryUpdateNameRequest (
    val oldName: String,
    val newName: String
)