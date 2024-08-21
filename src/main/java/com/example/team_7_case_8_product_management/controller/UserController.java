package com.example.team_7_case_8_product_management.controller;

import com.example.team_7_case_8_product_management.model.TokenModel;
import com.example.team_7_case_8_product_management.model.User;
import com.example.team_7_case_8_product_management.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@CrossOrigin
@RestController
@RequiredArgsConstructor
public class UserController {
    final UserService userService;

    @PostMapping("/v1/api/auth")
    public void registerUser(@RequestBody User user) {
        userService.registerUser(user);
    }

    @PostMapping("/v1/api/login")
    public TokenModel getToken(HttpServletRequest request, @RequestBody User user) {
        System.out.println(request.getRemoteAddr());
        return userService.loginUser(user);

    }

    @GetMapping("/v1/api/user")
    public User getUser() {
        return new User(1l, "misha", "misha", "kaskin", "USER", LocalDate.now(), 1f);
//        return userService.getUsers();
    }
}
