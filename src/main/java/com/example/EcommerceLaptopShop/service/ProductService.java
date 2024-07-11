package com.example.EcommerceLaptopShop.service;

import com.example.EcommerceLaptopShop.dto.ProductDto;
import com.example.EcommerceLaptopShop.entity.Configuration;
import com.example.EcommerceLaptopShop.entity.Product;
import com.example.EcommerceLaptopShop.repository.ConfigurationRepository;
import com.example.EcommerceLaptopShop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ConfigurationRepository configurationRepository;

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public List<Product> allProduct() {
        return productRepository.findAll();
    }


    public Product createProduct(Product product, Configuration configuration) {
        Configuration savedConfiguration = configurationRepository.save(configuration);
        product.set_deleted(false);
        product.set_activated(true);
        product.setConfiguration(savedConfiguration);
        return productRepository.save(product);
    }

    public void update(Product product, Configuration configuration) {
        configurationRepository.save(configuration);
        productRepository.save(product);
    }

    public void enableById(Long id) {
        Product product = productRepository.getById(id);
        product.set_activated(true);
        product.set_deleted(false);
        productRepository.save(product);
    }

    public void deleteById(Long id) {
        Product product = productRepository.getById(id);
        product.set_deleted(true);
        product.set_activated(false);
        productRepository.save(product);
    }

    public Product findById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Sản phẩm có id: " + id + " không tồn tại."));
    }

    public List<Product> randomProduct() {
        return productRepository.randomProduct();
    }


    private List<String> gamingLaptops = Arrays.asList("Acer Aspire 7", "Acer Nitro", "Asus ROG", "Asus TUF", "Dell Alienware", "Dell Gaming G15", "Lenovo IdeaPad Gaming",
            "Gigabyte G5", "Gigabyte Gaming AERO", "Gigabyte AORUS", "Gigabyte MF", "Lenovo Legion", "Lenovo LOG", "MSI Cyborg", "MSI Gaming Bravo", "MSI Gaming GF63", "MSI Katana");
    private List<String> officeLaptops = Arrays.asList("Acer Aspire 3", "Acer Swift", "Asus ExpertBook", "Asus VivoBook", "Asus ZenBook", "Dell Inspiron", "Dell XPS", "Lenovo IdeaPad", "Lenovo ThinkBook",
            "Lenovo Yoga", "Macbook Air", "Macbook Pro", "Vaio FE", "HP Pavilion", "HP Envy", "HP Victus", "HP Elitebook", "LG Gram");

    public List<Product> getGamingLaptop() {
        return productRepository.getAllProduct().stream()
                .filter(x -> gamingLaptops.contains(x.getCategory().getName()))
                .collect(Collectors.toList());
    }

    public List<Product> getOfficeLaptop() {
        return productRepository.getAllProduct().stream()
                .filter(x -> officeLaptops.contains(x.getCategory().getName()))
                .collect(Collectors.toList());
    }

    public List<Product> searchProducts(String keyword) {
        return productRepository.searchProducts(keyword);
    }

}

