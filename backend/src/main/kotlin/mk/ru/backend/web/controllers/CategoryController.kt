package mk.ru.backend.web.controllers

import mk.ru.backend.services.category.CategoryService
import mk.ru.backend.web.requests.CategoryCreateRequest
import mk.ru.backend.web.requests.CategoryUpdateNameRequest
import mk.ru.backend.web.responses.category.CategoryCreateResponse
import mk.ru.backend.web.responses.category.CategoryUpdateNameResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/category")
class CategoryController(private val categoryService: CategoryService) {
    @PostMapping
    fun createCategory(@RequestBody categoryCreateRequest: CategoryCreateRequest): ResponseEntity<CategoryCreateResponse> =
        ResponseEntity.ok(categoryService.create(categoryCreateRequest))

    @PatchMapping
    fun updateName(@RequestBody categoryUpdateNameRequest: CategoryUpdateNameRequest): ResponseEntity<CategoryUpdateNameResponse> =
        ResponseEntity.ok(categoryService.updateName(categoryUpdateNameRequest))
}