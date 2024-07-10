package com.example.EcommerceLaptopShop.controller.admin;

import com.example.EcommerceLaptopShop.entity.Order;
import com.example.EcommerceLaptopShop.entity.OrderDetail;
import com.example.EcommerceLaptopShop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller("adminOrderController")
@RequestMapping("/admin/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping
    public String getOrder(Model model) {
        List<Order> listOrders = orderService.getAll();
        model.addAttribute("orders", listOrders);
        return "admin/order";
    }

    @GetMapping("/detail/{id}")
    public String showDetail(@PathVariable("id") Long id, Model model) {
        List<OrderDetail> listDetails = orderService.getDetailOfOrder(id);
        model.addAttribute("details", listDetails);
        return "admin/order-detail";
    }

    @GetMapping("/accept-order")
    public String acceptOrder(Long id) {
        orderService.acceptOrder(id);
        return "redirect:/orders";
    }

    @GetMapping("/cancel-order")
    public String cancelOrder(Long id) {
        orderService.cancelOrder(id);
        return "redirect:/orders";
    }
}
