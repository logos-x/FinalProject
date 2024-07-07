package com.example.ecommercelaptopshop.library.repository;

import com.example.ecommercelaptopshop.library.entity.Order;
import com.example.ecommercelaptopshop.library.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
}
