package com.example.ecommercelaptopshop.library.dto;

import com.example.ecommercelaptopshop.library.entity.Customer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingCartDto {
    private Long id;
    private Customer customer;
    private double totalPrice;
    private int totalItems;
    private Set<CartItemDto> cartItems;
}
