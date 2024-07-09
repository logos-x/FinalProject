package com.example.EcommerceLaptopShop.repository;

import com.example.EcommerceLaptopShop.dto.BrandDto;
import com.example.EcommerceLaptopShop.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {
    @Query(value = "update Brand set name = ?1 where id = ?2")
    Brand update(String name, Long id);

    @Query(value = "select * from brands where is_activated = true", nativeQuery = true)
    List<Brand> findAllByActivatedTrue();

    @Query(value = "select new com.example.EcommerceLaptopShop.dto.BrandDto(b.id, b.name, count(p.category.brand.id)) " +
            "from Brand b left join Product p on b.id = p.category.brand.id " +
            "where b.activated = true and b.deleted = false " +
            "group by b.id ")
    List<BrandDto> getBrandsAndSize();
}