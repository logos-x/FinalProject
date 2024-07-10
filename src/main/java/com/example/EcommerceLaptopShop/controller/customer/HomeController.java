package com.example.EcommerceLaptopShop.controller.customer;

import com.example.EcommerceLaptopShop.repository.BrandRepository;
import com.example.EcommerceLaptopShop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
    @Autowired
    private BrandRepository brandRepository;
    @Autowired
    private ProductRepository productRepository;

    @GetMapping
    public String indexPage(Model model) {
        model.addAttribute("brands", brandRepository.findAllByActivatedTrue());
        model.addAttribute("products", productRepository.getAllProduct());
        return "customer/index";
    }
}
