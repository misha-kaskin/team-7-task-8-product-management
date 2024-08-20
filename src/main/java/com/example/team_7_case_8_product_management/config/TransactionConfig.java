package com.example.team_7_case_8_product_management.config;

import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class TransactionConfig {
    @Value("${auth.jwt.secret}")
    private String secretKey;
    @Bean
    Algorithm algorithm() {
        return Algorithm.HMAC256(secretKey);
    }
}
