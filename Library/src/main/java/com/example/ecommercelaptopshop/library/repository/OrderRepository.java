package com.example.ecommercelaptopshop.library.repository;

import com.example.ecommercelaptopshop.library.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
