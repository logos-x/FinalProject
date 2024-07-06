package com.example.ecommercelaptopshop.admin.controller;

import com.example.ecommercelaptopshop.library.entity.Brand;
import com.example.ecommercelaptopshop.library.entity.Category;
import com.example.ecommercelaptopshop.library.service.BrandService;
import com.example.ecommercelaptopshop.library.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller("adminCategoryController")
@RequestMapping("admin/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private BrandService brandService;

    @GetMapping("/categories")
    public String categories(Model model) {
        model.addAttribute("title", "Manage Category");
        List<Category> categories = categoryService.findALl();
        model.addAttribute("categories", categories);
        model.addAttribute("size", categories.size());
        model.addAttribute("categoryNew", new Category());
        return "admin/categories";
    }

    @PostMapping("/save-category")
    public String save(@ModelAttribute("categoryNew") Category category, Model model, RedirectAttributes redirectAttributes) {
        try {
            categoryService.save(category);
            model.addAttribute("categoryNew", category);
            redirectAttributes.addFlashAttribute("success", "Add successfully!");
        } catch (DataIntegrityViolationException e1) {
            e1.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Duplicate name of category, please check again!");
        } catch (Exception e2) {
            e2.printStackTrace();
            model.addAttribute("categoryNew", category);
            redirectAttributes.addFlashAttribute("error", "Error server");
        }
        return "redirect:/categories";
    }

    @GetMapping("/newBrand")
    public String showCategoryForm(Model model) {
        model.addAttribute("brand", new Brand());
        return "admin/brand-form";
    }

    @PostMapping("/newBrand")
    public String saveCategory(@ModelAttribute("brand") Brand brand) {
        brandService.save(brand);
        return "redirect:/admin/";
    }
}
