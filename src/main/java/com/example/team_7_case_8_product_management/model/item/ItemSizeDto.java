package com.example.team_7_case_8_product_management.model.item;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ItemSizeDto {

    private Long sizeId;
    private String title;
    private Long count;

}
