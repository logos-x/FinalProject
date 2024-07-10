package com.example.EcommerceLaptopShop.repository;

import com.example.EcommerceLaptopShop.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {

    @Query("select od from OrderDetail od inner join Order o on od.order.id = o.id where od.order.id = ?1")
    List<OrderDetail> getDetailOfOrder(Long id);
}
