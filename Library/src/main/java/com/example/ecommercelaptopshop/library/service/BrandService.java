package com.example.ecommercelaptopshop.library.service;

import com.example.ecommercelaptopshop.library.dto.BrandDto;
import com.example.ecommercelaptopshop.library.entity.Brand;
import com.example.ecommercelaptopshop.library.repository.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BrandService {
    @Autowired
    private BrandRepository brandRepository;

    public Brand save(Brand brand) {
        Brand brandSave = new Brand();
        brandSave.setName(brand.getName());
        brandSave.setActivated(true);
        brandSave.setDeleted(false);
        return brandRepository.save(brandSave);
    }

    public Brand update(Brand brand) {
        Brand brandUpdate = brandRepository.getReferenceById(brand.getId());
        brandUpdate.setName(brand.getName());
        return brandRepository.save(brandUpdate);
    }

    public List<Brand> findAllByActivatedTrue() {
        return brandRepository.findAllByActivatedTrue();
    }

    public List<Brand> findALl() {
        return brandRepository.findAll();
    }

    public Optional<Brand> findById(Long id) {
        return brandRepository.findById(id);
    }

    public void deleteById(Long id) {
        Brand category = brandRepository.getById(id);
        category.setActivated(false);
        category.setDeleted(true);
        brandRepository.save(category);
    }

    public void enableById(Long id) {
        Brand category = brandRepository.getById(id);
        category.setActivated(true);
        category.setDeleted(false);
        brandRepository.save(category);
    }

    public List<BrandDto> getBrandsAndSize() {
        List<BrandDto> brands = brandRepository.getBrandsAndSize();
        return brands;
    }
}

