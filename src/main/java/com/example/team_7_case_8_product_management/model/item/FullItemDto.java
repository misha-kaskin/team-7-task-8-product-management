package com.example.team_7_case_8_product_management.model.item;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FullItemDto {
    private Long itemId;
    private String type;
    private String productName;
    private String description;
    private Float price;
    private String image;
    private long[] sizes;
}
