package com.example.team_7_case_8_product_management.interceptor;

import com.example.team_7_case_8_product_management.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {
    private final AuthService authService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String token = request.getHeader("token");
        if (token == null) {
            String header = request.getHeader("access-control-request-headers");
            if (header == null) {
                return false;
            }
            return header.contains("token");
        }
        String path = request.getRequestURI().toUpperCase();
        authService.validateToken(token, path);
        return true;
    }

}
