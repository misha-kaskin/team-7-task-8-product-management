package com.example.team_7_case_8_product_management.controller;

import com.example.team_7_case_8_product_management.model.TokenModel;
import com.example.team_7_case_8_product_management.model.User;
import com.example.team_7_case_8_product_management.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
public class UserController {
    final UserService userService;

    @PostMapping("/v1/api/auth")
    public void registerUser(@RequestBody User user) {
        userService.registerUser(user);
    }

    @PostMapping("/v1/api/login")
    public ResponseEntity<TokenModel> getToken(@RequestBody User user) {
//        return userService.loginUser(user);
        return ResponseEntity.status(HttpStatus.CREATED)
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Headers", "Content-Type")
                .header("Access-Control-Allow-Credentials", "true")
                .header("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS,HEAD")
                .body(userService.loginUser(user));
    }

    @GetMapping("/v1/api/user")
    public User getUser() {
        return new User(1l, "misha", "misha", "kaskin", "USER", LocalDate.now());
//        return userService.getUsers();
    }
}
