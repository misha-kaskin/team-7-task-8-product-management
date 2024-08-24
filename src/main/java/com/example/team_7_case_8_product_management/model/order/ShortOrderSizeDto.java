package com.example.team_7_case_8_product_management.model.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShortOrderSizeDto {

    private Long sizeId;
    private Long count;

}
