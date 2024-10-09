package com.many.demo.dto;

import com.many.demo.entity.Category;
import com.many.demo.entity.Product;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
public class ProductDTO {
    private Long id;
    private String name;
    private Set<Long> categoryIds;

    // Static method to convert Product entity to ProductDTO
    public static ProductDTO fromEntity(Product product) {
        if (product == null) {
            return null; // Handle null product case
        }
        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());

        // Extracting category IDs from the product's categories
        dto.setCategoryIds(product.getCategories().stream()
                .map(Category::getId) // Assuming Category has a getId() method
                .collect(Collectors.toSet()));

        return dto;
    }
}
