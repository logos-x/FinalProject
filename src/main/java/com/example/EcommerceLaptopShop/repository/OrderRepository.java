package com.example.EcommerceLaptopShop.repository;

import com.example.EcommerceLaptopShop.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
