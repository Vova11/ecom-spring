package com.many.demo.dto;

import com.many.demo.entity.Product;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;
@Getter
@Setter
public class CategoryDTO {
    private Long id;
    private String name;
    private List<ProductDTO> productList;
    private String anotherField = "aasaa";




}