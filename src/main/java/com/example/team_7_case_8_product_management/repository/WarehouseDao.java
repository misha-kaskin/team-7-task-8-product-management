package com.example.team_7_case_8_product_management.repository;

import com.example.team_7_case_8_product_management.model.item.Item;
import com.example.team_7_case_8_product_management.model.warehouse.WarehouseEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface WarehouseDao extends CrudRepository<WarehouseEntity, Long> {

    @Query(
            "select wh.warehouseId.itemId " +
                    "from WarehouseEntity wh " +
                    "where wh.warehouseId.status.statusId = ?1 " +
                    "and wh.warehouseId.itemId.state.stateId = ?2"
    )
    List<Item> findAllByStatus(Long statusId, Long stateId);

    @Query(
            "select wh " +
                    "from WarehouseEntity wh " +
                    "where wh.warehouseId.itemId.itemId = ?1 " +
                    "and wh.warehouseId.status.statusId = ?2 " +
                    "and wh.warehouseId.itemId.state.stateId = ?3"
    )
    List<WarehouseEntity> findAllSaleByItemId(Long id, Long statusId, Long stateId);




}
