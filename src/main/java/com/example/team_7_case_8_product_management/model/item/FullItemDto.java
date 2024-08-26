package com.example.team_7_case_8_product_management.model.item;

import com.example.team_7_case_8_product_management.controller_advice.Marker;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class FullItemDto {

    @Null(groups = Marker.OnCreate.class)
    @NotNull(groups = Marker.OnUpdate.class)
    private Long itemId;

    @NotNull
    private ItemType type;

    @NotBlank
    private String productName;

    @NotBlank
    private String description;

    @PositiveOrZero
    private Float price;

    @NotNull
    private String image;

    @NotNull
    private Set<ItemSizeDto> sizes;

}
