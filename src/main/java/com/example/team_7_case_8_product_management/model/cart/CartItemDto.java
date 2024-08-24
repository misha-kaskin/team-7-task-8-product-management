package com.example.team_7_case_8_product_management.model.cart;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDto {

    private Long itemId;
    private String productName;
    private Float price;
    private String image;
    Set<SizeDto> sizes;

}
