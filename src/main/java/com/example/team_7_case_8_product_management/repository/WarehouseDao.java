package com.example.team_7_case_8_product_management.repository;

import com.example.team_7_case_8_product_management.model.item.Item;
import com.example.team_7_case_8_product_management.model.warehouse.WarehouseEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface WarehouseDao extends CrudRepository<WarehouseEntity, Long> {
    @Query("select wh.item from WarehouseEntity wh where wh.status = ?1")
    List<Item> findAllByStatus(String status);

    @Query("select wh from WarehouseEntity wh where wh.item.itemId = ?1 and wh.status = ?2")
    List<WarehouseEntity> findByItem(Long id, String status);
    void deleteAllByItem(Item item);
    boolean existsByItem(Item item);
}
