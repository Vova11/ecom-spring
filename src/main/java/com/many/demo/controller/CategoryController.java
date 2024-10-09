package com.many.demo.controller;

import com.many.demo.dto.CategoryDTO;
import com.many.demo.entity.Category;
import com.many.demo.service.impl.CategoryServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CategoryController {

    @Autowired
    private CategoryServiceImpl categoryService;

//    // GET: Retrieve all categories
//    @GetMapping("/public/categories")
//    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
//        List<CategoryDTO> categories = categoryService.getAllCategories();
//        return new ResponseEntity<>(categories, HttpStatus.OK);
//    }

    // GET: Retrieve all categories
    @GetMapping("/public/categories")
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }



    // POST: Create a new category
    @PostMapping("/public/categories")
    public ResponseEntity<Category> createCategory(@Valid @RequestBody Category category) {
        Category savedCategory = categoryService.saveCategory(category);
        return new ResponseEntity<>(savedCategory, HttpStatus.CREATED);
    }

//    // POST: Create a new category
//    @PostMapping("/public/categories")
//    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
//        // Save the category using the service
//        Category savedCategory = categoryService.saveCategory(categoryDTO);
//        // Convert the saved category to a DTO
//        CategoryDTO savedCategoryDTO = categoryService.toDTO(savedCategory);
//        // Return the saved category as DTO
//        return new ResponseEntity<>(savedCategoryDTO, HttpStatus.CREATED);
//    }

    @PutMapping("/admin/categories/{id}")
    public ResponseEntity<CategoryDTO> updateCategory(@PathVariable Long id, @Valid @RequestBody CategoryDTO categoryDTO) {
        CategoryDTO updatedCategoryDTO = categoryService.updateCategory(id, categoryDTO);
        return new ResponseEntity<>(updatedCategoryDTO, HttpStatus.OK);
    }



    // GET: Retrieve a category by ID
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable Long id) {
        CategoryDTO category = categoryService.getCategoryById(id);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    // GET: Retrieve a category and its associated products by category ID
    @GetMapping("/{id}/products")
    public ResponseEntity<CategoryDTO> getCategoryWithProducts(@PathVariable Long id) {
        CategoryDTO categoryDTO = categoryService.getCategoryWithProducts(id);
        return new ResponseEntity<>(categoryDTO, HttpStatus.OK);
    }

    @DeleteMapping("/admin/categories/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        String status = categoryService.deleteCategory(id);
        return new ResponseEntity<>(status, HttpStatus.OK);
    }

}