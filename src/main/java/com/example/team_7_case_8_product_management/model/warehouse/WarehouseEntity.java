package com.example.team_7_case_8_product_management.model.warehouse;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "warehouses")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WarehouseEntity {

    @EmbeddedId
    private EmbeddedWarehouseId warehouseId;

    private Long count;

}
