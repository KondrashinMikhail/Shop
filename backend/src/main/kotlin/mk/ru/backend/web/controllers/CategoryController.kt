package mk.ru.backend.web.controllers

import mk.ru.backend.annotations.CommonController
import mk.ru.backend.services.category.CategoryService
import mk.ru.backend.web.requests.category.CategoryCreateRequest
import mk.ru.backend.web.requests.category.CategoryUpdateNameRequest
import mk.ru.backend.web.responses.category.CategoryCreateResponse
import mk.ru.backend.web.responses.category.CategoryInfoResponse
import mk.ru.backend.web.responses.category.CategoryUpdateNameResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping

@CommonController
@RequestMapping("/category")
class CategoryController(private val categoryService: CategoryService) {
    @PostMapping
    fun createCategory(@RequestBody categoryCreateRequest: CategoryCreateRequest): ResponseEntity<CategoryCreateResponse> =
        ResponseEntity.ok(categoryService.create(categoryCreateRequest))

    @PatchMapping("/{name}")
    fun updateName(
        @PathVariable name: String,
        @RequestBody categoryUpdateNameRequest: CategoryUpdateNameRequest
    ): ResponseEntity<CategoryUpdateNameResponse> =
        ResponseEntity.ok(categoryService.updateName(name, categoryUpdateNameRequest))

    @GetMapping
    fun getAll(): ResponseEntity<List<CategoryInfoResponse>> = ResponseEntity.ok(categoryService.findAll())
}