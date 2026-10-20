package org.example.foodstore.Controller;


import org.example.foodstore.FoodDao.Food;
import org.example.foodstore.FoodDao.FoodCreationRequest;
import org.example.foodstore.FoodDao.BulkDeleteResponse;
import org.example.foodstore.Response.ApiResponse;
import org.example.foodstore.Service.FoodService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/foods")
@CrossOrigin(origins = "*")
@Tag(name = "Food Management", description = "APIs for managing food items, including CRUD operations and bulk delete")
public class FoodController {

    private final FoodService foodService;

    public FoodController(FoodService foodService) {
        this.foodService = foodService;
    }

    // GET: http://localhost:8080/api/foods/health
    @GetMapping("/health")
    public ResponseEntity<ApiResponse<String>> healthCheck() {
        return ResponseEntity.ok(ApiResponse.success("Foodstore API is up and running!", null));
    }

    // 1. Get all foods
    // GET: http://localhost:8080/api/foods
    @GetMapping
    public ResponseEntity<ApiResponse<List<Food>>> getAllFoods() {
        return ResponseEntity.ok(ApiResponse.success("Foods retrieved successfully", foodService.getAllFoods()));
    }

}
