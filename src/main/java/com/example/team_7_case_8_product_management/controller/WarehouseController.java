package com.example.team_7_case_8_product_management.controller;

import com.example.team_7_case_8_product_management.model.item.FullItemDto;
import com.example.team_7_case_8_product_management.model.item.ItemDto;
import com.example.team_7_case_8_product_management.model.warehouse.WarehouseEntity;
import com.example.team_7_case_8_product_management.service.ItemService;
import com.example.team_7_case_8_product_management.service.WarehouseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequiredArgsConstructor
public class WarehouseController {

    private final WarehouseService warehouseService;
    private final ItemService itemService;

    @GetMapping("/v1/api/admin/wh")
    public List<FullItemDto> getAll() {
        return itemService.getManagerItems();
    }

    @PatchMapping("/v1/api/manager/no-sale")
    public void noSaleItem(@Valid @RequestBody ItemDto item) {
        warehouseService.writeOffItem(item, 1l, 2l);
    }

    @PatchMapping("/v1/api/manager/in-stock")
    public void inStockItems(@Valid @RequestBody ItemDto item) {
        warehouseService.writeOffItem(item, 2l, 1l);
    }

}
