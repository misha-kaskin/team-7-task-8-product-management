package com.example.team_7_case_8_product_management.controller;

import com.example.team_7_case_8_product_management.model.TokenModel;
import com.example.team_7_case_8_product_management.model.User;
import com.example.team_7_case_8_product_management.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {
    final UserService userService;

    @PostMapping("/v1/api/auth")
    public void registerUser(@RequestBody User user) {
        userService.registerUser(user);
    }

    @PostMapping("/v1/api/login")
    public TokenModel getToken(@RequestBody User user) {
        return userService.loginUser(user);
    }

    @GetMapping("/v1/api/user")
    public User getUser() {
        return new User(1l, "misha", "misha", "kaskin", "USER", LocalDate.now());
//        return userService.getUsers();
    }
}
