package com.example.team_7_case_8_product_management.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ItemDto {
    private Long itemId;
    private String productName;
    private String type;
    private String description;
    private Float price;
    private String image;
    private long[] sizes;
}
