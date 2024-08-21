package com.example.team_7_case_8_product_management.repository;

import com.example.team_7_case_8_product_management.model.WarehouseEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface WarehouseDao extends CrudRepository<WarehouseEntity, Long> {
    List<WarehouseEntity> findAllByStatus(String status);
}
