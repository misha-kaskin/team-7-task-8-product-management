package com.example.team_7_case_8_product_management.model.cart;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SizeDto {

    private Long sizeId;
    private String title;
    private Long count;

}
