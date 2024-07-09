package com.example.EcommerceLaptopShop.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "passwordresettoken")
public class PasswordResetToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;

    private LocalDateTime expiryDateTime;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User user;
}
