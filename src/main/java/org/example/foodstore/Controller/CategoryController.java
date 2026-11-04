package org.example.foodstore.Controller;

import org.example.foodstore.FoodDao.Category;
import org.example.foodstore.Response.ApiResponse;
import org.example.foodstore.Service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/categories")
@CrossOrigin(origins = "*")
@Tag(name = "Category Management", description = "APIs for managing food categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // 1. Get all categories
    // GET: http://localhost:8080/api/categories
    @GetMapping
    public ResponseEntity<ApiResponse<List<Category>>> getAllCategorys() {
        return ResponseEntity.ok(ApiResponse.success("Categorys retrieved successfully", categoryService.getAllCategorys()));
    }

    // 2. Get category by ID
    // GET: http://localhost:8080/api/categories/1
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Category>> getCategoryById(
            @Parameter(description = "ID of the category to retrieve", example = "1") @PathVariable int id) {
        Optional<Category> category = categoryService.getCategoryById(id);
        return category.map(a -> ResponseEntity.ok(ApiResponse.success("Category retrieved successfully", a)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

}
