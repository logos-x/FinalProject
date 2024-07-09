package com.example.EcommerceLaptopShop.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
@Entity
@Table(name = "order_details")
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_detail_id")
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "order_id", referencedColumnName = "order_id")
    private Order order;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;

    @Min(value = 1, message = "Số lượng mua không được nhỏ hơn 1")
    private int quantity;
}
