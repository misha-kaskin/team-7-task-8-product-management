package com.example.team_7_case_8_product_management.model.warehouse;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "warehouse_status")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WarehouseStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long statusId;

    private String title;

}
