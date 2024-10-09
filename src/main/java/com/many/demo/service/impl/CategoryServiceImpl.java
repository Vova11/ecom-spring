package com.many.demo.service.impl;

import com.many.demo.dto.CategoryDTO;
import com.many.demo.dto.ProductDTO;
import com.many.demo.entity.Category;
import com.many.demo.exceptions.ApiException;
import com.many.demo.exceptions.ResourceNotFoundException;
import com.many.demo.repository.CategoryRepository;
import com.many.demo.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        if (categories.isEmpty())
            throw new ApiException("No categories created till now");
        return categories;
    }

//    @Override
//    public List<CategoryDTO> getAllCategories() {
//        List<Category> categories = categoryRepository.findAll();
//        return categories.stream()
//                .map(this::toDTO)
//                .collect(Collectors.toList());
//    }

    // Save a new category
    public Category saveCategory(Category category) {
        Category savedCategory = categoryRepository.findByName(category.getName());
        if (savedCategory != null)
            throw new ApiException("Category with the name " + category.getName() + " already exists !!!");
        return categoryRepository.save(category);
    }

    @Override
    public String deleteCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));
        categoryRepository.delete(category);
        return "Category with id " + id + " was deleted successfully";
    }

    @Override
    public CategoryDTO getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));
        return toDTO(category);
    }

    // Get a category with associated products by ID
    @Override
    public CategoryDTO getCategoryWithProducts(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() ->  new ResourceNotFoundException("Category", "id", id));
        return toDTO(category);
    }

    public CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO) {
        Category category = categoryRepository.findById(id).orElseThrow(() ->  new ResourceNotFoundException("Category", "id", id));
        category.setName(categoryDTO.getName());
        Category updatedCategory = categoryRepository.save(category);
        return toDTO(updatedCategory);

    }

    // Convert Category to CategoryDTO
    public CategoryDTO toDTO(Category category) {
        CategoryDTO dto = new CategoryDTO();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setProductList(category.getProducts().stream()
                .map(ProductDTO::fromEntity) // Using the fromEntity method
                .collect(Collectors.toList()));
        return dto;
    }
}
