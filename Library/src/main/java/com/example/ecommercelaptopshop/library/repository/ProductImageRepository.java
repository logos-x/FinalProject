package com.example.ecommercelaptopshop.library.repository;

import com.example.ecommercelaptopshop.library.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
}
