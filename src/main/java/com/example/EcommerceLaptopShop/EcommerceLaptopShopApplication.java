package com.example.EcommerceLaptopShop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"com.example.ecommercelaptopshop.library",
		"com.example.ecommercelaptopshop.customer",
		"com.example.ecommercelaptopshop.admin",
		"com.example.ecommercelaptopshop.library.service"})
@EntityScan(basePackages = {"com.example.EcommerceLaptopShop.library.entity"})
@EnableJpaRepositories(basePackages = {"com.example.ecommercelaptopshop.library.repository"})
public class EcommerceLaptopShopApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcommerceLaptopShopApplication.class, args);
	}

}
