package com.example.team_7_case_8_product_management.model.item;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "item_types")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long typeId;

    private String title;

}
