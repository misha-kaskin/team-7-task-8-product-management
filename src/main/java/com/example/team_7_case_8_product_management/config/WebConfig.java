package com.example.team_7_case_8_product_management.config;

import com.example.team_7_case_8_product_management.interceptor.AdminInterceptor;
import com.example.team_7_case_8_product_management.interceptor.AuthInterceptor;
import com.example.team_7_case_8_product_management.interceptor.ManagerInterceptor;
import com.example.team_7_case_8_product_management.interceptor.UserInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final AuthInterceptor authInterceptor;
    private final AdminInterceptor adminInterceptor;
    private final UserInterceptor userInterceptor;
    private final ManagerInterceptor managerInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        String login = "/v1/api/login";
        String register = "/v1/api/auth";
        String admin = "/v1/api/admin/*";
        String user = "/v1/api/user/*";
        String manager = "/v1/api/manager/*";

        registry.addInterceptor(authInterceptor).excludePathPatterns(login, register);
        registry.addInterceptor(adminInterceptor).excludePathPatterns(login, register, user, manager);
        registry.addInterceptor(userInterceptor).excludePathPatterns(login, register, admin, manager);
        registry.addInterceptor(managerInterceptor).excludePathPatterns(login, register, admin, user);
    }
}
