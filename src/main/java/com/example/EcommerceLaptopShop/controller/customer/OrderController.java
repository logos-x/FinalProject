package com.example.EcommerceLaptopShop.controller.customer;

import com.example.EcommerceLaptopShop.entity.CartItem;
import com.example.EcommerceLaptopShop.service.CartService;
import com.example.EcommerceLaptopShop.service.OrderService;
import com.example.EcommerceLaptopShop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller("customerOrderController")
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private CartService cartService;

    @GetMapping("/checkout")
    public String checkout() {
        return "customer/checkout";
    }
    @PostMapping("/create")
    public String submitOrder(String customerName, String customerEmail, String customerPhone, String customerAddress) {
        List<CartItem> cartItems = cartService.getCartItems();
        if (cartItems.isEmpty()) {
            return "redirect:/cart";
        }
        orderService.createOrder(customerName, customerAddress, customerPhone, customerEmail, cartItems);
        return "redirect:/orders/confirmation";
    }
    @GetMapping("/confirmation")
    public String orderConfirmation(Model model) {
        model.addAttribute("message", "Your order has been successfully placed.");
        return "customer/order-confirm";
    }
}
