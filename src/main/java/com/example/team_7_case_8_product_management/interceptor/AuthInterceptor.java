package com.example.team_7_case_8_product_management.interceptor;

import com.example.team_7_case_8_product_management.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Enumeration;
import java.util.Iterator;

@Component
@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {
    private final AuthService authService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String token = request.getHeader("token");
        Enumeration<String> headerNames = request.getHeaderNames();
        Iterator<String> stringIterator = headerNames.asIterator();
        while (stringIterator.hasNext()) {
            String headerName = stringIterator.next();
            System.out.println(headerName + " : " + request.getHeader(headerName));
        }
        String path = request.getRequestURI().toUpperCase();
        authService.validateToken(token, path);
        return true;
    }

}
