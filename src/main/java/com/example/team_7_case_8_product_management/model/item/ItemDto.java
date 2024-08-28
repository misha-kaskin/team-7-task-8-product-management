package com.example.team_7_case_8_product_management.model.item;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class ItemDto {

    @NotNull
    private Long itemId;

    @NotNull
    @NotEmpty
    private Set<ItemSizeDto> sizes;

}
