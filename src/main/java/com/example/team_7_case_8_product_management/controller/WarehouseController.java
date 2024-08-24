package com.example.team_7_case_8_product_management.controller;

import com.example.team_7_case_8_product_management.model.warehouse.WarehouseEntity;
import com.example.team_7_case_8_product_management.service.WarehouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class WarehouseController {

    private final WarehouseService warehouseService;

    @GetMapping("/v1/api/admin/wh")
    public Iterable<WarehouseEntity> getAll() {
        return warehouseService.getAll();
    }
}
