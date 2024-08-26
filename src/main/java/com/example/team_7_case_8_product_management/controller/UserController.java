package com.example.team_7_case_8_product_management.controller;

import com.example.team_7_case_8_product_management.model.TokenModel;
import com.example.team_7_case_8_product_management.model.user.*;
import com.example.team_7_case_8_product_management.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/v1/api/login")
    public TokenModel getToken(@Valid @RequestBody LoginUserDto user) {
        return userService.loginUser(user);
    }

    @PostMapping("/v1/api/refresh")
    public TokenModel refreshToken(@RequestHeader("token") String token) {
        return userService.refreshToken(token);
    }

    @GetMapping("/v1/api/admin/users")
    public Iterable<GetUserDto> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/v1/api/admin/users/{id}")
    public GetUserDto getUsersById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PostMapping("/v1/api/admin/registration")
    public void regUser(@Valid @RequestBody UserDto user) {
        userService.registerUser(user);
    }

    @PatchMapping("/v1/api/admin/user-edit")
    public void editUser(@Valid @RequestBody EditUserDto user) {
        userService.editUser(user);
    }

    @DeleteMapping("/v1/api/admin/user-remove/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

}
