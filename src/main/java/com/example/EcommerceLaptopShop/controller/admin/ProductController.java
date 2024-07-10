package com.example.EcommerceLaptopShop.controller.admin;

import com.example.EcommerceLaptopShop.entity.*;
import com.example.EcommerceLaptopShop.service.BrandService;
import com.example.EcommerceLaptopShop.service.CategoryService;
import com.example.EcommerceLaptopShop.service.ProductImageService;
import com.example.EcommerceLaptopShop.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
        return "redirect:/admin/products";
    }

    private String saveImageStatic(MultipartFile image) throws IOException {
        File saveFile = new ClassPathResource("static/images").getFile();
        String fileName = UUID.randomUUID()+ "." + StringUtils.getFilenameExtension(image.getOriginalFilename());
        Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + fileName);
        Files.copy(image.getInputStream(), path);
        return fileName;
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Product product = productService.findById(id);
        if (product != null) {
            model.addAttribute("product", product);
            model.addAttribute("brands", brandService.findAllByActivatedTrue());
            model.addAttribute("categories", categoryService.findAllByActivatedTrue());
            return "admin/edit-form";
        }
        return "redirect:/admin/products";
    }

    @PostMapping("/edit/{id}")
    public String updateProduct(@PathVariable("id") Long id,
                                @Valid @ModelAttribute("product") Product product,
                                BindingResult result, Model model,
                                @RequestParam("image") MultipartFile imageFile) {
        if (result.hasErrors()) {
            model.addAttribute("brands", brandService.findAllByActivatedTrue());
            return "admin/edit-form";
        }

        Product existingProduct = productService.findById(id);
        Configuration configuration = product.getConfiguration();
        if (!imageFile.isEmpty()) {
            try {
                String imageName = saveImageStatic(imageFile);
                product.setThumbnail("/images/" + imageName);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else {
            product.setThumbnail(existingProduct.getThumbnail());
        }

        productService.update(product, configuration);
        return "redirect:/admin/products";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") Long id) {
        productService.deleteById(id);
        return "redirect:/products";
    }
}
