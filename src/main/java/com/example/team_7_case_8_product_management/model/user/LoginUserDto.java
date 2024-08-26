package com.example.team_7_case_8_product_management.model.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginUserDto {

    @NotBlank
    private String login;

    @NotBlank
    private String password;

}
