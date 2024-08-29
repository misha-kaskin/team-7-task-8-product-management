package com.example.team_7_case_8_product_management.exception;

import com.example.team_7_case_8_product_management.model.cart.ExtendCartDto;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class TooManyItemsException extends RuntimeException {
    private final ExtendCartDto extendCartDto;
}
