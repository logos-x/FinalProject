package com.example.EcommerceLaptopShop.controller.customer;

import com.example.EcommerceLaptopShop.repository.BrandRepository;
import com.example.EcommerceLaptopShop.repository.ProductRepository;
import com.example.EcommerceLaptopShop.service.BrandService;
import com.example.EcommerceLaptopShop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
    @Autowired
    private BrandService brandService;
    @Autowired
    private ProductService productService;

    @GetMapping
    public String indexPage(Model model) {
        model.addAttribute("brands", brandService.findAllByActivatedTrue());
        model.addAttribute("products", productService.findAll());
        model.addAttribute("gamings", productService.getGamingLaptop());
        model.addAttribute("offices", productService.getOfficeLaptop());
        model.addAttribute("hotdeals", productService.randomProduct());
        return "customer/index";
    }
}
