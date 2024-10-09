package com.many.demo.service;

import com.many.demo.dto.ProductDTO;
import com.many.demo.entity.Category;
import com.many.demo.entity.Product;
import com.many.demo.exceptions.ResourceNotFoundException;
import com.many.demo.repository.CategoryRepository;
import com.many.demo.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public Product saveProductWithCategories(ProductDTO productDTO) {
        Product product = new Product();
        product.setName(productDTO.getName());

        Set<Category> categories = productDTO.getCategoryIds().stream()
                .map(id -> categoryRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Category", "id", id)))
                .collect(Collectors.toSet());

        product.setCategories(categories);
        return productRepository.save(product);
    }

    // Get all products
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // Get a product by ID
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    @Transactional
    public ProductDTO updateProduct(ProductDTO productDTO) {
        Product product = productRepository.findById(productDTO.getId())
                .orElseThrow(() ->  new ResourceNotFoundException("Product", "id", productDTO.getId()));

        // Update basic attributes
        product.setName(productDTO.getName());
        // Update other attributes as needed...
        //TODO: Update other attributes as needed

        // Update categories
        Set<Category> updatedCategories = new HashSet<>();
        for (Long categoryId : productDTO.getCategoryIds()) {
            Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));
            updatedCategories.add(category);
        }
        product.setCategories(updatedCategories);

        // Save the updated product
        Product savedProduct = productRepository.save(product);

        // Convert back to DTO and return
        return ProductDTO.fromEntity(savedProduct);
    }


}