package com.example.team_7_case_8_product_management.controller;

import com.example.team_7_case_8_product_management.model.TokenModel;
import com.example.team_7_case_8_product_management.model.User;
import com.example.team_7_case_8_product_management.model.UserDto;
import com.example.team_7_case_8_product_management.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/v1/api/auth")
    public void registerUser(@RequestBody UserDto user) {
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

    @PostMapping("/v1/api/admin/registration")
    public void regUser(@RequestBody UserDto user) {
        userService.registerUser(user);
    }

    @PatchMapping("/v1/api/admin/user-edit")
    public void editUser(@RequestBody User user) {
        userService.editUser(user);
    }
}
