package courseproject.library.service;

import courseproject.library.dto.ProductDto;
import courseproject.library.entity.Product;
import courseproject.library.repository.IProductRepository;
import courseproject.library.utils.ImageUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class ProductService {
    @Autowired
    private IProductRepository productRepository;
    @Autowired
    private ImageUpload imageUpload;

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public List<ProductDto> products() {
        return transferData(productRepository.getAllProduct());
    }

    public List<ProductDto> allProduct() {
        List<Product> products = productRepository.findAll();
        List<ProductDto> productDtos = transferData(products);
        return productDtos;
    }

    public Product save(MultipartFile imageProduct, ProductDto productDto) {
        Product product = new Product();
        try {
            if (imageProduct == null) {
                product.setThumbnail(null);
            } else {
                imageUpload.uploadFile(imageProduct);
                product.setThumbnail(Base64.getEncoder().encodeToString(imageProduct.getBytes()));
            }
            product.setName(productDto.getName());
            product.setDescription(productDto.getDescription());
            product.setQuantityStock(productDto.getQuantityStock());
            product.setOriginalPrice(productDto.getOriginalPrice());
            product.setCategory(productDto.getCategory());
            product.set_deleted(false);
            product.set_activated(true);
            return productRepository.save(product);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Product update(MultipartFile imageProduct, ProductDto productDto) {
        try {
            Product productUpdate = productRepository.getReferenceById(productDto.getId());
            if (imageProduct.getBytes().length > 0) {
                if (imageUpload.checkExist(imageProduct)) {
                    productUpdate.setThumbnail(productUpdate.getThumbnail());
                } else {
                    imageUpload.uploadFile(imageProduct);
                    productUpdate.setThumbnail(Base64.getEncoder().encodeToString(imageProduct.getBytes()));
                }
            }
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

    public List<ProductDto> randomProduct() {
        return transferData(productRepository.randomProduct());
    }

    public Page<ProductDto> searchProducts(int pageNo, String keyword) {
        List<Product> products = productRepository.findAllByNameOrDescription(keyword);
        List<ProductDto> productDtoList = transferData(products);
        Pageable pageable = PageRequest.of(pageNo, 5);
        Page<ProductDto> dtoPage = toPage(productDtoList, pageable);
        return dtoPage;
    }

    public Page<ProductDto> getAllProducts(int pageNo) {
        Pageable pageable = PageRequest.of(pageNo, 6);
        List<ProductDto> productDtoLists = this.allProduct();
        Page<ProductDto> productDtoPage = toPage(productDtoLists, pageable);
        return productDtoPage;
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
