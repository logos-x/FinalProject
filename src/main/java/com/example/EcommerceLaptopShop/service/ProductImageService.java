package com.example.EcommerceLaptopShop.service;

import com.example.EcommerceLaptopShop.entity.ProductImage;
import com.example.EcommerceLaptopShop.repository.ProductImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductImageService {
    @Autowired
    private ProductImageRepository productImageRepository;

    public List<ProductImage> getAllImages() {
        return productImageRepository.findAll();
    }

    public void addImage(ProductImage productImage) {
        productImageRepository.save(productImage);
    }
}
