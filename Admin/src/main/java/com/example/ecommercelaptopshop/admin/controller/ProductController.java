package com.example.ecommercelaptopshop.admin.controller;

import com.example.ecommercelaptopshop.library.dto.ProductDto;
import com.example.ecommercelaptopshop.library.entity.*;
import com.example.ecommercelaptopshop.library.service.BrandService;
import com.example.ecommercelaptopshop.library.service.CategoryService;
import com.example.ecommercelaptopshop.library.service.ProductImageService;
import com.example.ecommercelaptopshop.library.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.List;
import java.util.UUID;

@Controller("adminProductController")
@RequestMapping("admin/products")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private BrandService brandService;
    @Autowired
    private ProductImageService imageService;


    @GetMapping()
    public String allProduct(Model model) {
        List<Product> products = productService.allProduct();
        model.addAttribute("products", products);
        model.addAttribute("brands", brandService.findAllByActivatedTrue());
        return "admin/products-list";
    }

    @GetMapping("/newProduct")
    public String addProductPage(Model model) {
        model.addAttribute("title", "Add Product");
        List<Brand> brands = brandService.findAllByActivatedTrue();
        List<Category> categories = categoryService.findAllByActivatedTrue();
        model.addAttribute("brands", brands);
        model.addAttribute("categories", categories);
        model.addAttribute("product", new Product());
        return "admin/add-product";
    }

    @PostMapping("/newProduct")
    public String saveProduct(@ModelAttribute("product") Product product,
                              @RequestParam("image") MultipartFile imageFile,
                              @RequestParam("anotherImage") MultipartFile[] anotherImage,
                              BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("brands", brandService.findAllByActivatedTrue());
            model.addAttribute("categories", categoryService.findAllByActivatedTrue());
            return "admin/add-product";
        }
        Configuration configuration = product.getConfiguration();
        if (!imageFile.isEmpty()) {
            try {
                String imageName = saveImageStatic(imageFile);
                product.setThumbnail("/images/" + imageName);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        productService.createProduct(product, configuration);
        for (MultipartFile image : anotherImage) {
            if (!image.isEmpty()) {
                try {
                    String imageUrl = saveImageStatic(image);
                    ProductImage detailImage = new ProductImage();
                    detailImage.setImagePath("/images/" + imageUrl);
                    detailImage.setProduct(product);
                    product.getImages().add(detailImage);
                    imageService.addImage(detailImage);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
        return "redirect:";
    }

    private String saveImageStatic(MultipartFile image) throws IOException {
        File saveFile = new ClassPathResource("static/images").getFile();
        String fileName = UUID.randomUUID()+ "." + StringUtils.getFilenameExtension(image.getOriginalFilename());
        Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + fileName);
        Files.copy(image.getInputStream(), path);
        return fileName;
    }
}
