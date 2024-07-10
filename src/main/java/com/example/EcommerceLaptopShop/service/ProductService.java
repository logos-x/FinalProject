package com.example.EcommerceLaptopShop.service;

import com.example.EcommerceLaptopShop.dto.ProductDto;
import com.example.EcommerceLaptopShop.entity.Configuration;
import com.example.EcommerceLaptopShop.entity.Product;
import com.example.EcommerceLaptopShop.repository.ConfigurationRepository;
import com.example.EcommerceLaptopShop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ConfigurationRepository configurationRepository;

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public List<ProductDto> products() {
        return transferData(productRepository.getAllProduct());
    }

    public List<Product> allProduct() {
        return productRepository.findAll();
    }


    public Product createProduct(Product product, Configuration configuration) {
        Configuration savedConfiguration = configurationRepository.save(configuration);
        product.set_deleted(false);
        product.set_activated(true);
        product.setConfiguration(savedConfiguration);
        return productRepository.save(product);
    }


    public Product update(MultipartFile imageProduct, ProductDto productDto) {
        try {
            Product productUpdate = productRepository.getReferenceById(productDto.getId());
            productUpdate.setCategory(productDto.getCategory());
            productUpdate.setId(productUpdate.getId());
            productUpdate.setName(productDto.getName());
            productUpdate.setDescription(productDto.getDescription());
            productUpdate.setOriginalPrice(productDto.getOriginalPrice());
            productUpdate.setDiscountedPrice(productDto.getDiscountedPrice());
            productUpdate.setQuantityStock(productDto.getQuantityStock());
            return productRepository.save(productUpdate);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void enableById(Long id) {
        Product product = productRepository.getById(id);
        product.set_activated(true);
        product.set_deleted(false);
        productRepository.save(product);
    }

    public void deleteById(Long id) {
        Product product = productRepository.getById(id);
        product.set_deleted(true);
        product.set_activated(false);
        productRepository.save(product);
    }

    public ProductDto getById(Long id) {
        ProductDto productDto = new ProductDto();
        Product product = productRepository.getById(id);
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setOriginalPrice(product.getOriginalPrice());
        productDto.setDiscountedPrice(product.getDiscountedPrice());
        productDto.setQuantityStock(product.getQuantityStock());
        productDto.setCategory(product.getCategory());
        productDto.setThumbnail(product.getThumbnail());
        productDto.setImages(new ArrayList<>(product.getImages()));
        return productDto;
    }

    public Product findById(Long id) {
        return productRepository.findById(id).get();
    }

    public List<Product> randomProduct() {
        return productRepository.randomProduct();
    }

    public Page<ProductDto> searchProducts(int pageNo, String keyword) {
        List<Product> products = productRepository.findAllByNameOrDescription(keyword);
        List<ProductDto> productDtoList = transferData(products);
        Pageable pageable = PageRequest.of(pageNo, 5);
        Page<ProductDto> dtoPage = toPage(productDtoList, pageable);
        return dtoPage;
    }

    public Page<Product> getAllProducts(int pageNo) {
        Pageable pageable = PageRequest.of(pageNo, 6);
        List<Product> productLists = this.allProduct();
        Page<Product> productPage = toPage(productLists, pageable);
        return productPage;
    }

    private List<String> gamingLaptops = Arrays.asList("Acer Aspire 7", "Acer Nitro", "Asus ROG", "Asus TUF", "Dell Alienware", "Dell Gaming G15", "Lenovo IdeaPad Gaming",
            "Gigabyte G5", "Gigabyte Gaming AERO", "Gigabyte AORUS", "Gigabyte MF", "Lenovo Legion", "Lenovo LOG", "MSI Cyborg", "MSI Gaming Bravo", "MSI Gaming GF63", "MSI Katana");
    private List<String> officeLaptops = Arrays.asList("Acer Aspire 3", "Acer Swift", "Asus ExpertBook", "Asus VivoBook", "Asus ZenBook", "Dell Inspiron", "Dell XPS", "Lenovo IdeaPad", "Lenovo ThinkBook",
            "Lenovo Yoga", "Macbook Air", "Macbook Pro", "Vaio FE", "HP Pavilion", "HP Envy", "HP Victus", "HP Elitebook", "LG Gram");

    public List<Product> getGamingLaptop() {
        return productRepository.getAllProduct().stream()
                .filter(x -> gamingLaptops.contains(x.getCategory().getName()))
                .collect(Collectors.toList());
    }

    public List<Product> getOfficeLaptop() {
        return productRepository.getAllProduct().stream()
                .filter(x -> officeLaptops.contains(x.getCategory().getName()))
                .collect(Collectors.toList());
    }


    public Page<ProductDto> getAllProductsForCustomer(int pageNo) {
        return null;
    }

    public List<ProductDto> findAllByCategory(String category) {
        return transferData(productRepository.findAllByCategory(category));
    }

    public List<ProductDto> filterHighProducts() {
        return transferData(productRepository.filterHighProducts());
    }

    public List<ProductDto> filterLowerProducts() {
        return transferData(productRepository.filterLowerProducts());
    }

    public List<ProductDto> listViewProducts() {
        return transferData(productRepository.listViewProduct());
    }

    public List<ProductDto> findByCategoryId(Long id) {
        return transferData(productRepository.getProductByCategoryId(id));
    }

    public List<ProductDto> searchProducts(String keyword) {
        return transferData(productRepository.searchProducts(keyword));
    }

    private Page toPage(List list, Pageable pageable) {
        if (pageable.getOffset() >= list.size()) {
            return Page.empty();
        }
        int startIndex = (int) pageable.getOffset();
        int endIndex = ((pageable.getOffset() + pageable.getPageSize()) > list.size())
                ? list.size()
                : (int) (pageable.getOffset() + pageable.getPageSize());
        List subList = list.subList(startIndex, endIndex);
        return new PageImpl(subList, pageable, list.size());
    }

    private List<ProductDto> transferData(List<Product> products) {
        List<ProductDto> productDtos = new ArrayList<>();
        for (Product product : products) {
            ProductDto productDto = new ProductDto();
            productDto.setId(product.getId());
            productDto.setName(product.getName());
            productDto.setQuantityStock(product.getQuantityStock());
            productDto.setOriginalPrice(product.getOriginalPrice());
            productDto.setDiscountedPrice(product.getDiscountedPrice());
            productDto.setDescription(product.getDescription());
            productDto.setThumbnail(product.getThumbnail());
            productDto.setImages(new ArrayList<>(product.getImages()));
            productDto.setCategory(product.getCategory());
            productDto.set_activated(product.is_activated());
            productDto.set_deleted(product.is_deleted());
            productDtos.add(productDto);
        }
        return productDtos;
    }
}

