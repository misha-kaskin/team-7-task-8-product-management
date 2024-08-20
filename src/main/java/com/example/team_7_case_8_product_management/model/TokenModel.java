package com.example.team_7_case_8_product_management.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenModel {
    private String token;
    private Long expire;
}
