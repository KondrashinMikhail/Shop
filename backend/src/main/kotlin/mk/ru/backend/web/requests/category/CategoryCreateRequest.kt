package mk.ru.backend.web.requests.category

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

data class CategoryCreateRequest @JsonCreator constructor(
    @field:JsonProperty("name") val name: String
)
