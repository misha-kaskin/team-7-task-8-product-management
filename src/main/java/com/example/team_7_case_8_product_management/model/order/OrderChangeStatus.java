package com.example.team_7_case_8_product_management.model.order;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NonNull;

@Data
public class OrderChangeStatus {

    @NotNull
    private Long orderId;

    @NotNull
    private Long orderStatus;

}
