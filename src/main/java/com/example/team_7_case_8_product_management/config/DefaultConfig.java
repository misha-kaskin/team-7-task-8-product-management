package com.example.team_7_case_8_product_management.config;

import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DefaultConfig {

    @Value("${auth.jwt.secret}")
    private String secretKey;

    @Bean
    public Algorithm algorithm() {
        return Algorithm.HMAC256(secretKey);
    }

}
