package com.batch5.Create_Task_Application.taskModule.controller;

import com.batch5.Create_Task_Application.commonModule.ApiResponse;
import com.batch5.Create_Task_Application.taskModule.entity.Category;
import com.batch5.Create_Task_Application.taskModule.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @PostMapping
    public ResponseEntity<ApiResponse<Category>> createCategory(@RequestBody Category category) {

        Category savedCategory = categoryService.saveCategory(category);

        ApiResponse<Category> response = new ApiResponse<>(
                201,
                "Category created successfully",
                savedCategory
        );

        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Category>>> getCategories() {

        List<Category> categories = categoryService.getAllCategories();

        ApiResponse<List<Category>> response = new ApiResponse<>(
                200,
                "Categories fetched successfully",
                categories
        );

        return new ResponseEntity<>(response,HttpStatus.OK);
    }
}
