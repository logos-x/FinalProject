package com.example.EcommerceLaptopShop.repository;

import com.example.EcommerceLaptopShop.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("select p from Product p where p.is_deleted = false and p.is_activated = true")
    List<Product> getAllProduct();

    @Query("select p from Product p where p.name like %?1% or p.description like %?1%")
    List<Product> findAllByNameOrDescription(String keyword);


    @Query("select p from Product p inner join Category c ON c.id = p.category.id" +
            " where p.category.name = ?1 and p.is_activated = true and p.is_deleted = false")
    List<Product> findAllByCategory(String category);

    @Query("select p from Product p inner join Brand b ON b.id = p.category.brand.id" +
            " where p.category.name = ?1 and p.is_activated = true and p.is_deleted = false")
    List<Product> findAllByBrand(String brand);

    @Query(value = "select " +
            "p.id, p.name, p.description, p.quantity, p.original_price, p.category_id, p.discount_price, p.thumbnail, p.is_activated, p.is_deleted, p.configuration_id " +
            "from products p where p.is_activated = true and p.is_deleted = false order by rand() limit 5", nativeQuery = true)
    List<Product> randomProduct();

    @Query(value = "select " +
            "p.id, p.name, p.description, p.quantity, p.original_price, p.category_id, p.discount_price, p.thumbnail, p.is_activated, p.is_deleted, p.configuration_id " +
            "from products p where p.is_deleted = false and p.is_activated = true order by p.original_price desc limit 9", nativeQuery = true)
    List<Product> filterHighProducts();

    @Query(value = "select " +
            "p.product_id, p.name, p.description, p.current_quantity, p.cost_price, p.category_id, p.sale_price, p.image, p.is_activated, p.is_deleted " +
            "from products p where p.is_deleted = false and p.is_activated = true order by p.cost_price asc limit 9", nativeQuery = true)
    List<Product> filterLowerProducts();


    @Query(value = "select p.id, p.name, p.description, p.quantityStock, p.originalPrice, p.category.name, p.discountedPrice, p.thumbnail, p.is_activated, p.is_deleted from products p where p.is_deleted = false and p.is_activated = true limit 4", nativeQuery = true)
    List<Product> listViewProduct();


    @Query(value = "select p from Product p inner join Category c on c.id = ?1 and p.category.id = ?1 where p.is_activated = true and p.is_deleted = false")
    List<Product> getProductByCategoryId(Long id);

    @Query(value = "select p from Product p inner join Brand b on b.id = ?1 and p.category.brand.id = ?1 where p.is_activated = true and p.is_deleted = false")
    List<Product> getProductByBrandId(Long id);


    @Query("select p from Product p where p.name like %?1% or p.description like %?1%")
    List<Product> searchProducts(String keyword);
}

