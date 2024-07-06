package com.example.ecommercelaptopshop.library.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "original_price")
    private double originalPrice;

    @Column(name = "discount_price")
    private double discountedPrice;

    @Column(name = "description")
    private String description;

    @Column(name = "thumbnail")
    private String thumbnail;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductImage> images = new ArrayList<>();

    @Column(name = "quantity")
    private int quantityStock;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "configuration_id", referencedColumnName = "id")
    private Configuration configuration;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    private boolean is_activated;
    private boolean is_deleted;
}
