package com.example.team_7_case_8_product_management.model.user;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class GetUserDto {

    private Long userId;
    private String name;
    private String login;
    private String role;
    private LocalDate registerDate;
    private Float balance;
    private Long cartCount;

}
