package com.example.team_7_case_8_product_management.model.cart;

import com.example.team_7_case_8_product_management.model.item.FullItemDto;
import lombok.Builder;
import lombok.Data;

import java.util.Collection;
import java.util.Set;

@Data
@Builder
public class ExtendCartDto {

    private Long userId;
    private Collection<CartItemDto> items;
    private Collection<ExtendItemDto> exceed;
    private Set<FullItemDto> deleted;
    private Set<FullItemDto> archive;
    private Float amount;

}
