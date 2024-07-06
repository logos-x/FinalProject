package com.example.ecommercelaptopshop.customer.controller;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("customerProductController")
@RequestMapping("/shop/products")
public class ProductController {
    @GetMapping("/menu")
    public String allProduct(Model model) {
        model.addAttribute("title", "Hello Admin");
        return "index";
    }
}
