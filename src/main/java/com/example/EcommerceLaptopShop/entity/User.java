package com.example.EcommerceLaptopShop.entity;

import com.example.EcommerceLaptopShop.validator.ValidUsername;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", length = 50, nullable = false, unique = true)
    @NotBlank(message = "Tên đăng nhập không được để trống")
    @Size(max = 50, message = "Tên đăng nhập phải ít hơn 50 ký tự")
    @ValidUsername
    private String username;

    @Column(name = "password", length = 250, nullable = false)
    @NotBlank(message = "Mật khẩu không được để trống")
    private String password;

    @Column(name = "email", length = 50)
    @Size(max = 50, message = "Email phải ít hơn 50 ký tự")
    private String email;

    @Column(name = "name", length = 50, nullable = false)
    @Size(max = 50, message = "Tên của bạn phải ít hơn 50 ký tự")
    @NotBlank(message = "Tên của bạn không được để trống")
    private String name;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<PasswordResetToken> passwordResetTokens;
}
