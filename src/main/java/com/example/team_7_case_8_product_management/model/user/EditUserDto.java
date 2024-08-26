package com.example.team_7_case_8_product_management.model.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class EditUserDto {

    @NotNull
    private Long userId;

    @NotBlank
    private String name;

    private String role;

    @PositiveOrZero
    private Float balance;

}
