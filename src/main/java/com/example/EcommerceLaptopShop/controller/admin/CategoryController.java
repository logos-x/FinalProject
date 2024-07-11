package com.example.EcommerceLaptopShop.controller.admin;

import com.example.EcommerceLaptopShop.entity.Brand;
import com.example.EcommerceLaptopShop.entity.Category;
import com.example.EcommerceLaptopShop.entity.Configuration;
import com.example.EcommerceLaptopShop.entity.Product;
import com.example.EcommerceLaptopShop.service.BrandService;
import com.example.EcommerceLaptopShop.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("admin/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private BrandService brandService;

    @GetMapping
    public String categories(Model model) {
        model.addAttribute("brands", brandService.findAllByActivatedTrue());
        model.addAttribute("categories", categoryService.findAllByActivatedTrue());
        return "admin/categories-list";
    }

    @GetMapping("/add-category")
    public String showCategoryForm(Model model) {
        model.addAttribute("brands", brandService.findAllByActivatedTrue());
        model.addAttribute("category", new Category());
        return "admin/category-form";
    }
    @PostMapping("/add-category")
    public String save(@ModelAttribute("categoryNew") Category category, Model model, BindingResult result) {
        if (result.hasErrors()) {
            model.addAttribute("brands", brandService.findAllByActivatedTrue());
            return "admin/category-form";
        }
        return "redirect:/categories";
    }

    @GetMapping("/edit-category/{id}")
    public String showEditCategoryForm(@PathVariable("id") Long id, Model model) {
        Category category = categoryService.findById(id);
        if (category != null) {
            model.addAttribute("brands", brandService.findAllByActivatedTrue());
            model.addAttribute("category", category);
            return "admin/edit-category";
        }
        return "redirect:/admin/categories";
    }

    @PostMapping("/edit-category/{id}")
    public String updateCategory(@PathVariable("id") Long id,
                              @Valid @ModelAttribute("category") Category category,
                              BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("brands", brandService.findAllByActivatedTrue());
            return "admin/edit-category";
        }
        categoryService.update(category);
        return "redirect:/admin/categories";
    }

    @GetMapping("/delete-category/{id}")
    public String deleteCategory(@PathVariable("id") Long id) {
        categoryService.deleteById(id);
        return "redirect:/admin/categories";
    }

    @GetMapping("/enable-category/{id}")
    public String enableCategory(@PathVariable("id") Long id) {
        categoryService.enableById(id);
        return "redirect:/admin/categories";
    }

    @GetMapping("/add-brand")
    public String showBrandForm(Model model) {
        model.addAttribute("brand", new Brand());
        return "admin/brand-form";
    }

    @PostMapping("/add-brand")
    public String saveBrand(@ModelAttribute("brand") Brand brand) {
        brandService.save(brand);
        return "redirect:/admin/categories";
    }

    @GetMapping("/edit-brand/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Brand brand = brandService.findById(id);
        if (brand != null) {
            model.addAttribute("brand", brand);
            return "admin/edit-brand";
        }
        return "redirect:/admin/categories";
    }

    @PostMapping("/edit-brand/{id}")
    public String updateBrand(@PathVariable("id") Long id,
                                @Valid @ModelAttribute("brand") Brand brand,
                                BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "admin/edit-brand";
        }
        brandService.update(brand);
        return "redirect:/admin/categories";
    }

    @GetMapping("/delete-brand/{id}")
    public String deleteBrand(@PathVariable("id") Long id) {
        brandService.deleteById(id);
        return "redirect:/admin/categories";
    }

    @GetMapping("/enable-brand/{id}")
    public String enableBrand(@PathVariable("id") Long id) {
        brandService.enableById(id);
        return "redirect:/admin/categories";
    }
}
