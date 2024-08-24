package com.example.team_7_case_8_product_management.model.item;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ShortItemDto {
    private Long itemId;
    private String productName;
    private String type;
    private Float price;
    private String image;
}
