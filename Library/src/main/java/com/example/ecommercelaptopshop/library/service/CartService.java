package com.example.ecommercelaptopshop.library.service;

import com.example.ecommercelaptopshop.library.entity.CartItem;
import com.example.ecommercelaptopshop.library.entity.Product;
import com.example.ecommercelaptopshop.library.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.util.ArrayList;
import java.util.List;

@Service
@SessionScope
public class CartService {
    private List<CartItem> cartItems = new ArrayList<>();

    @Autowired
    private ProductRepository productRepository;

    public void addToCard(Long productID, int quantity) {
        Product product = productRepository.findById(productID)
                .orElseThrow(() -> new IllegalArgumentException("Product not found: " + productID));
        int availableQuantity = product.getQuantityStock();
        CartItem productInCart = cartItems.stream().filter(item -> item.getProduct().getId() == productID)
                .findFirst().orElse(null);
        if (productInCart != null) {
            if (quantity + productInCart.getQuantity() > availableQuantity) {
                throw new RuntimeException("Sản phẩm trong kho không đủ");
            } else {
                productInCart.setQuantity(quantity + productInCart.getQuantity());
            }
        } else {
            if (quantity > availableQuantity) {
                throw new RuntimeException("Sản phẩm trong kho không đủ");
            } else {
                cartItems.add(new CartItem(product, quantity));
            }
        }

    }

    public void updateCart(Long productID, int quantity) {
        Product product = productRepository.findById(productID)
                .orElseThrow(() -> new IllegalArgumentException("Product not found: " + productID));
        int availableQuantity = product.getQuantityStock();
        CartItem productInCart = cartItems.stream().filter(item -> item.getProduct().getId() == productID)
                .findFirst().orElse(null);
        if (productInCart != null) {
            if (quantity > availableQuantity) {
                throw new RuntimeException("Sản phẩm trong kho không đủ");
            } else {
                productInCart.setQuantity(quantity);
            }
        }
    }

    public List<CartItem> getCartItems() {
        return cartItems.stream().toList();
    }

    public void removeFromCart(Long productID) {
        cartItems.removeIf(item -> item.getProduct().getId().equals(productID));
    }

    public void clearCart() {
        cartItems.clear();
    }

    public double total() {
        return cartItems.stream().mapToDouble(item -> item.getProduct().getDiscountedPrice() * item.getQuantity()).sum();
    }
}