package com.example.EcommerceLaptopShop.repository;

import com.example.EcommerceLaptopShop.entity.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfigurationRepository extends JpaRepository<Configuration, Long> {
}
