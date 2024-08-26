package com.example.team_7_case_8_product_management.model.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    @NotBlank
    private String name;

    @NotBlank
    private String login;

    @NotBlank
    private String password;

    private String role;

    @PositiveOrZero
    private Float balance;

}
