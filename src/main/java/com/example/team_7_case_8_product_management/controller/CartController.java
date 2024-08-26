package com.example.team_7_case_8_product_management.controller;

import com.example.team_7_case_8_product_management.model.user.User;
import com.example.team_7_case_8_product_management.model.cart.Cart;
import com.example.team_7_case_8_product_management.model.cart.CartDto;
import com.example.team_7_case_8_product_management.service.CartService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@CrossOrigin
@RestController
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping("/v1/api/admin/cart/{id}")
    public void addItemToCart(@RequestBody CartDto cart, @PathVariable Long id) {
        cartService.addItemToCart(cart, id);
    }

    @GetMapping("/v1/api/admin/cart")
    public Iterable<Cart> getAllCarts() {
        return cartService.getAllCarts();
    }

    @GetMapping("/v1/api/admin/cart/{id}")
    public CartDto getAllItemsByUserId(@PathVariable Long id) {
        return cartService.getItemsByUserId(id);
    }

    @DeleteMapping("/v1/api/admin/cart")
    public void deleteAll(@RequestBody User user) {
        cartService.deleteAllById(user.getUserId());
    }

    @DeleteMapping("/v1/api/admin/cart/{userId}/{itemId}/{sizeId}")
    public void deleteAllById(
            @NotNull @PathVariable Long userId,
            @NotNull @PathVariable Long itemId,
            @NotNull @PathVariable Long sizeId
    ) {
        cartService.deleteById(userId, itemId, sizeId);
    }

}
