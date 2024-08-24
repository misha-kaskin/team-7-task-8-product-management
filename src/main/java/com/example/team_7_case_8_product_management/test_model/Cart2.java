package com.example.team_7_case_8_product_management.test_model;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
//@Entity
//@Table(name = "cart5")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cart2 {
    @EmbeddedId
    private EmbeddedCartId2 cartId;

    private Integer count;
}
