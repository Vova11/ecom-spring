package com.many.demo.service;

import com.many.demo.dto.CategoryDTO;
import com.many.demo.entity.Category;

import java.util.List;

public interface CategoryService {
//    List<CategoryDTO> getAllCategories();
    List<Category> getAllCategories();
    Category saveCategory(Category category);
    String deleteCategory(Long id);
    CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO);
    CategoryDTO getCategoryById(Long id);
    CategoryDTO getCategoryWithProducts(Long id);
}
