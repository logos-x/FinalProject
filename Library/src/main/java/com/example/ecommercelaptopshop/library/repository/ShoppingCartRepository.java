package com.example.ecommercelaptopshop.library.repository;

import com.example.ecommercelaptopshop.library.entity.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
}

