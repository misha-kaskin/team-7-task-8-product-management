package com.example.team_7_case_8_product_management.test_model;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

//@RestController
@RequiredArgsConstructor
public class TestController {

    private final TestService testService;

    @PostMapping("/v1/api/admin/test")
    public void add(@RequestBody Cart2 cart) {
        System.out.println(cart);
        testService.add(cart);
    }

    @GetMapping("/v1/api/admin/test")
    public Iterable<Cart2> getAll() {
        return testService.getAll();
    }

}
