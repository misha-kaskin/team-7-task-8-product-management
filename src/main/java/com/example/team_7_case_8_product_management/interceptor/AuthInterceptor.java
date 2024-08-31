package com.example.team_7_case_8_product_management.interceptor;

import com.example.team_7_case_8_product_management.exception.UserNotAuthException;
import com.example.team_7_case_8_product_management.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.BufferedReader;

@Component
@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {
    private final AuthService authService;

    @Override
    @SneakyThrows
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String token = request.getHeader("token");
        String path = request.getRequestURI().toUpperCase();
        System.out.println(path);
        if (path != null
                && (path.contains("/SWAGGER-UI/INDEX.HTML")
                || path.contains("/SWAGGER-UI/SWAGGER-INITIALIZER.JS"))
                || path.contains("/V3/API-DOCS/SWAGGER-CONFIG")
                || path.contains("/V3/API-DOCS")) return true;
        if (token == null) {
            String header = request.getHeader("access-control-request-headers");
            if (header == null) {
                throw new UserNotAuthException();
            } else {
                return header.contains("token");
            }
        }

        authService.validateToken(token, path);
        return true;
    }

}
