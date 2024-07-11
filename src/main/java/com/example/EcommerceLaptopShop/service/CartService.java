package com.example.EcommerceLaptopShop.service;

import com.example.EcommerceLaptopShop.entity.CartItem;
import com.example.EcommerceLaptopShop.entity.Product;
import com.example.EcommerceLaptopShop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Service
@SessionScope
public class CartService {
    private List<CartItem> cartItems = new ArrayList<>();

    @Autowired
    private ProductRepository productRepository;

    public void addToCard(Long id, int quantity, RedirectAttributes redirectAttributes) {
        Product product = productRepository.getReferenceById(id);
        int availableQuantity = product.getQuantityStock();
        CartItem productInCart = cartItems.stream().filter(item -> item.getProduct().getId() == id)
                .findFirst().orElse(null);
        if (productInCart != null) {
            if (quantity + productInCart.getQuantity() > availableQuantity) {
                redirectAttributes.addFlashAttribute("errorMessage", "Số lượng sản phẩm không đủ!");
            } else {
                productInCart.setQuantity(quantity + productInCart.getQuantity());
            }
        } else {
            if (quantity > availableQuantity) {
                redirectAttributes.addFlashAttribute("errorMessage", "Số lượng sản phẩm không đủ!");
            } else {
                cartItems.add(new CartItem(product, quantity));
            }
        }
    }

    public void increaseQuantity(Long productId) {
        for (CartItem item : cartItems) {
            if (item.getProduct().getId().equals(productId)) {
                item.setQuantity(item.getQuantity() + 1);
                return;
            }
        }
    }
    public void updateCartItem(Long productId, int quantity) {
        for (CartItem item : cartItems) {
            if (item.getProduct().getId().equals(productId)) {
                if (quantity > item.getProduct().getQuantityStock()) {
                    throw new IllegalArgumentException("Không đủ hàng trong kho cho sản phẩm: " + item.getProduct().getName());
                }
                if (quantity <= 0) {
                    throw new IllegalArgumentException("Số lượng phải lớn hơn 0.");
                }
                item.setQuantity(quantity);
                return;
            }
        }
        throw new IllegalArgumentException("Product not found in cart: " + productId);
    }
    public void decreaseQuantity(Long productId) {
        for (CartItem item : cartItems) {
            if (item.getProduct().getId().equals(productId)) {
                if (item.getQuantity() > 1) {
                    item.setQuantity(item.getQuantity() - 1);
                } else {
                    cartItems.remove(item);
                }
                return;
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