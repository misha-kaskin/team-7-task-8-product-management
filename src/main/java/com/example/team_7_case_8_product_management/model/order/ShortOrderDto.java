package com.example.team_7_case_8_product_management.model.order;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShortOrderDto {

    private Long userId;
    private Long orderId;
    private OrderStatus status;
    private LocalDate orderDate;
    private String firstName;
    private String secondName;
    private String lastName;
    private String address;
    private Set<OrderItemDto> items;

}
