package com.example.team_7_case_8_product_management.service;

import com.example.team_7_case_8_product_management.model.warehouse.WarehouseEntity;
import com.example.team_7_case_8_product_management.repository.WarehouseDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WarehouseService {

    private final WarehouseDao warehouseDao;

    public Iterable<WarehouseEntity> getAll() {
        return warehouseDao.findAll();
    }

}
