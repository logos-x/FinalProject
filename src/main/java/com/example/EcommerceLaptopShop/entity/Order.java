package com.example.EcommerceLaptopShop.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;
    private Date orderDate;
    private String orderStatus;
    private double totalPrice;
    private String customerName;
    private String customerPhone;
    private String customerEmail;
    private String customerAddress;
    private boolean isAccept;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order")
    private List<OrderDetail> orderDetailList;
}