package com.example.team_7_case_8_product_management.controller;

import com.example.team_7_case_8_product_management.model.order.OrderDto;
import com.example.team_7_case_8_product_management.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/v1/api/admin/order")
    public void addOrder(@RequestBody OrderDto orderDto) {
        orderService.addOrder(orderDto);
    }

}
