package com.example.team_7_case_8_product_management.test_model;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

//@Service
@RequiredArgsConstructor
public class TestService {

    private final CartDao2 cartDao;

    public Iterable<Cart2> getAll() {
        return cartDao.findAll();
    }

    public void add(Cart2 cart) {
        cartDao.save(cart);
    }
}
