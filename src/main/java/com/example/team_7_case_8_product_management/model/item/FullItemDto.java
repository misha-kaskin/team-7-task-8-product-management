package com.example.team_7_case_8_product_management.model.item;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class FullItemDto {

    private Long itemId;
    private ItemType type;
    private String productName;
    private String description;
    private Float price;
    private String image;
    private Set<ItemSizeDto> sizes;

}
