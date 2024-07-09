package com.example.EcommerceLaptopShop.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "brands")
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "is_activated")
    private boolean activated;

    @Column(name = "is_deleted")
    private boolean deleted;
}

