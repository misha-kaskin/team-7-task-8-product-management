package com.example.team_7_case_8_product_management.model.order;

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
public class OrderDto {

    private Long userId;
    private Long statusId;
    private LocalDate orderDate;
    private String firstName;
    private String secondName;
    private String lastName;
    private String address;
    private Set<ShortOrderItemDto> items;

}
