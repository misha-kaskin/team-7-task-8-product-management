package com.example.team_7_case_8_product_management.controller;

import com.example.team_7_case_8_product_management.controller_advice.Marker;
import com.example.team_7_case_8_product_management.model.item.Item;
import com.example.team_7_case_8_product_management.model.item.FullItemDto;
import com.example.team_7_case_8_product_management.model.item.ManagerItems;
import com.example.team_7_case_8_product_management.model.item.ShortItemDto;
import com.example.team_7_case_8_product_management.service.ItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@Validated
@CrossOrigin
@RestController
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @Validated({Marker.OnCreate.class})
    @PostMapping("/v1/api/admin/item")
    public void addItem(@Valid @RequestBody FullItemDto item) {
        itemService.addItem(item);
    }

    @GetMapping("/v1/api/item")
    public Collection<ShortItemDto> getItems() {
        return itemService.getSaleItems();
    }

    @DeleteMapping("/v1/api/admin/item")
    public void deleteItem(@RequestBody FullItemDto item) {
        itemService.deleteItem(item);
    }

    @PatchMapping("/v1/api/admin/item-archive/{id}")
    public void archiveItem(@PathVariable Long id) {
        itemService.archiveItem(id);
    }

    @GetMapping("/v1/api/admin/item")
    public ManagerItems getAdminItems() {
        return itemService.getManagerItems();
    }

    @GetMapping("/v1/api/manager/item")
    public ManagerItems getManagerItems() {
        return itemService.getManagerItems();
    }

    @GetMapping("/v1/api/item/{id}")
    public FullItemDto getItemById(@PathVariable Long id) {
        return itemService.getFullItemDtoById(id, 1l);
    }

    @Validated(Marker.OnUpdate.class)
    @PatchMapping("/v1/api/admin/item")
    public void updateItem(@Valid @RequestBody FullItemDto itemDto) {
        itemService.updateItem(itemDto, 1l);
    }

}
