package com.example.EcommerceLaptopShop.service;

import com.example.EcommerceLaptopShop.dto.BrandDto;
import com.example.EcommerceLaptopShop.entity.Brand;
import com.example.EcommerceLaptopShop.repository.BrandRepository;
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

    public Brand findById(Long id) {
        return brandRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Hãng có id: " + id + " không tồn tại."));
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

