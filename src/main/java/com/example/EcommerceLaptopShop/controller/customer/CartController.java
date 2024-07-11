package com.example.EcommerceLaptopShop.controller.customer;

import com.example.EcommerceLaptopShop.entity.CartItem;
import com.example.EcommerceLaptopShop.entity.Product;
import com.example.EcommerceLaptopShop.service.CartService;
import com.example.EcommerceLaptopShop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("cart")
public class CartController {
    @Autowired
    private CartService cartService;
    @Autowired
    private ProductService productService;



    @GetMapping
    public String showCart(Model model) {
        model.addAttribute("cartItems", cartService.getCartItems());
        model.addAttribute("total", cartService.total());
        return "customer/your-cart";
    }

    @PostMapping("/add-to-cart")
    public String addToCart(@RequestParam Long id, @RequestParam int quantity, RedirectAttributes redirectAttributes) {
        cartService.addToCard(id, quantity, redirectAttributes);
        return "redirect:/cart";
    }

    @GetMapping("/remove/{id}")
    public String removeFromCart(@PathVariable Long id) {
        cartService.removeFromCart(id);
        return "redirect:/cart";
    }
    @GetMapping("/clear")
    public String clearCart() {
        cartService.clearCart();
        return "redirect:/cart";
    }
    @PostMapping("/increase/{id}")
    public String increaseQuantity(@PathVariable Long id) {
        cartService.increaseQuantity(id);
        return "redirect:/cart";
    }

    @PostMapping("/decrease/{id}")
    public String decreaseQuantity(@PathVariable Long id) {
        cartService.decreaseQuantity(id);
        return "redirect:/cart";
    }
    @GetMapping("/update/{id}")
    public String updateCartItem(@PathVariable Long id, @RequestParam int quantity) {
        try {
            cartService.updateCartItem(id, quantity);
        } catch (IllegalArgumentException e) {
            // Handle error case, perhaps by adding an error message to the model
        }
        return "redirect:/cart";
    }

}
