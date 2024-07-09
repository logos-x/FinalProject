package com.example.EcommerceLaptopShop.controller.customer;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("shopProductController")
@RequestMapping("/shop/products")
public class ProductController {
    @GetMapping("/menu")
    public String allProduct(Model model) {
        model.addAttribute("title", "Hello Admin");
        return "customner/index";
    }
}
