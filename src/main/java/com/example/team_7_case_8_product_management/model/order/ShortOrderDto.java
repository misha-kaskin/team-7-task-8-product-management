package com.example.team_7_case_8_product_management.model.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShortOrderDto {

    private Long orderId;
    private Long userId;
    private OrderStatus status;
    private Set<OrderItemDto> items;

}
