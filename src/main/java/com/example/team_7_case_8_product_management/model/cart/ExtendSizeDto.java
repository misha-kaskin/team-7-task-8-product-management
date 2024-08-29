package com.example.team_7_case_8_product_management.model.cart;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExtendSizeDto {

    private Long sizeId;
    private String title;
    private Long cartCount;
    private Long whCount;

}
