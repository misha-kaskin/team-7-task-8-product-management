package com.example.team_7_case_8_product_management.controller;

import com.example.team_7_case_8_product_management.model.order.OrderDto;
import com.example.team_7_case_8_product_management.model.order.ShortOrderDto;
import com.example.team_7_case_8_product_management.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@CrossOrigin
@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/v1/api/admin/order/{id}")
    public void addOrder(@RequestBody OrderDto orderDto) {
        orderService.addOrder(orderDto);
    }

    @GetMapping("/v1/api/admin/order/{id}")
    public Collection<ShortOrderDto> getUserOrder(@PathVariable Long id) {
        return orderService.getOrdersByUserId(id);
    }

}
