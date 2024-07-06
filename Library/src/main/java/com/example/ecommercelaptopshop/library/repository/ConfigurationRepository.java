package com.example.ecommercelaptopshop.library.repository;

import com.example.ecommercelaptopshop.library.entity.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfigurationRepository extends JpaRepository<Configuration, Long> {
}
