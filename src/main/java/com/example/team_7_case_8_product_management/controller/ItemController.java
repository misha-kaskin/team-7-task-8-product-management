package com.example.team_7_case_8_product_management.controller;

import com.example.team_7_case_8_product_management.model.Item;
import com.example.team_7_case_8_product_management.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @PostMapping("/v1/api/item")
    public void addItem(@RequestBody Item item) {
        itemService.addItem(item);
    }
}
