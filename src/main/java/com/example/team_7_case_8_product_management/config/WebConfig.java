package com.example.team_7_case_8_product_management.config;

import com.example.team_7_case_8_product_management.interceptor.AuthInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final AuthInterceptor authInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        String login = "/v1/api/login";
        String register = "/v1/api/auth";
        registry.addInterceptor(authInterceptor).excludePathPatterns(login, register);
    }

}
