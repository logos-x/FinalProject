package com.example.ecommercelaptopshop.library.dto;

import com.example.ecommercelaptopshop.library.entity.Category;
import com.example.ecommercelaptopshop.library.entity.Configuration;
import com.example.ecommercelaptopshop.library.entity.ProductImage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    private Long id;
    private String name;
    private double originalPrice;
    private double discountedPrice;
    private String description;
    private String thumbnail;
    private List<ProductImage> images;
    private int quantityStock;
    private Configuration configuration;
    private Category category;
    private boolean is_activated;
    private boolean is_deleted;
}
