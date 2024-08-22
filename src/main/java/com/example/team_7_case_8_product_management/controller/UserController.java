package com.example.team_7_case_8_product_management.controller;

import com.example.team_7_case_8_product_management.model.TokenModel;
import com.example.team_7_case_8_product_management.model.User;
import com.example.team_7_case_8_product_management.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
    public TokenModel getToken(@RequestBody User user) {
        return userService.loginUser(user);
    }

    @GetMapping("/v1/api/admin/users")
    public Iterable<User> getUser() {
        return userService.getUsers();
    }
}
