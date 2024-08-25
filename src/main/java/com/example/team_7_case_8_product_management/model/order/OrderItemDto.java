package com.example.team_7_case_8_product_management.model.order;

import lombok.*;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"productName", "price", "image", "sizes"})
public class OrderItemDto {

    private Long itemId;
    private String productName;
    private Float price;
    private String image;
    private Set<OrderSizeDto> sizes;

}
