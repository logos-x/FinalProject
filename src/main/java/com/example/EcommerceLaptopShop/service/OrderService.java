package com.example.EcommerceLaptopShop.service;

import com.example.EcommerceLaptopShop.entity.*;
import com.example.EcommerceLaptopShop.repository.OrderDetailRepository;
import com.example.EcommerceLaptopShop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private CartService cartService;

    @Transactional
    public Order createOrder(String customerName, List<CartItem> cartItems) {
        Order order = new Order();
        order.setCustomerName(customerName);
        order = orderRepository.save(order);
        for (CartItem item : cartItems) {
            OrderDetail detail = new OrderDetail();
            detail.setOrder(order);
            detail.setProduct(item.getProduct());
            detail.setQuantity(item.getQuantity());
            orderDetailRepository.save(detail);
        }

        cartService.clearCart();
        return order;
    }

    public List<Order> getAll() {
        return orderRepository.findAll();
    }

    public List<OrderDetail> getDetailOfOrder(Long id) {
        return orderDetailRepository.getDetailOfOrder(id);
    }

    public Order acceptOrder(Long id) {
        Order order = orderRepository.getById(id);
        order.setAccept(true);
        return orderRepository.save(order);
    }

    public void cancelOrder(Long id) {
        orderRepository.deleteById(id);
    }
}
