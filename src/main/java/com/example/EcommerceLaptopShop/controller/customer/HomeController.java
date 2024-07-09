package com.example.EcommerceLaptopShop.controller.customer;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/shop")
public class HomeController {
    @GetMapping
    public String indexPage() {
        return "customer/index";
    }
}
