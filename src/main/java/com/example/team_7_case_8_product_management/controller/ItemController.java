package com.example.team_7_case_8_product_management.controller;

import com.example.team_7_case_8_product_management.model.Item;
import com.example.team_7_case_8_product_management.model.ItemDto;
import com.example.team_7_case_8_product_management.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@CrossOrigin
@RestController
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @PostMapping("/v1/api/admin/item")
    public void addItem(@RequestBody ItemDto item) {
        itemService.addItem(item);
    }

    @GetMapping("/v1/api/admin/item")
    public Collection<ItemDto> getItems() {
        return itemService.getSaleItems();
    }

    @DeleteMapping("/v1/api/admin/item")
    public void deleteItem(@RequestBody ItemDto item) {
        itemService.deleteItem(item);
    }

    @PutMapping("/v1/api/admin/item")
    public Iterable<Item> getAllItems() {
        return itemService.getAllItems();
    }

}
