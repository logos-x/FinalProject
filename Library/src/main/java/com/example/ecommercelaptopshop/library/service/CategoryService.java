package com.example.ecommercelaptopshop.library.service;

import com.example.ecommercelaptopshop.library.dto.CategoryDto;
import com.example.ecommercelaptopshop.library.entity.Category;
import com.example.ecommercelaptopshop.library.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public Category save(Category category) {
        Category categorySave = new Category();
        categorySave.setName(category.getName());
        categorySave.setBrand(category.getBrand());
        categorySave.setActivated(true);
        categorySave.setDeleted(false);
        return categoryRepository.save(categorySave);

    }

    public Category update(Category category) {
        Category categoryUpdate = categoryRepository.getReferenceById(category.getId());
        categoryUpdate.setName(category.getName());
        return categoryRepository.save(categoryUpdate);
    }

    public List<Category> findAllByActivatedTrue() {
        return categoryRepository.findAllByActivatedTrue();
    }

    public List<Category> findALl() {
        return categoryRepository.findAll();
    }

    public Optional<Category> findById(Long id) {
        return categoryRepository.findById(id);
    }

    public void deleteById(Long id) {
        Category category = categoryRepository.getById(id);
        category.setActivated(false);
        category.setDeleted(true);
        categoryRepository.save(category);
    }

    public void enableById(Long id) {
        Category category = categoryRepository.getById(id);
        category.setActivated(true);
        category.setDeleted(false);
        categoryRepository.save(category);
    }

    public List<CategoryDto> getCategoriesAndSize() {
        List<CategoryDto> categories = categoryRepository.getCategoriesAndSize();
        return categories;
    }
}
