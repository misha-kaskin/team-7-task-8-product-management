package com.example.team_7_case_8_product_management.service;

import com.example.team_7_case_8_product_management.exception.ItemNotFoundException;
import com.example.team_7_case_8_product_management.model.SizeEntity;
import com.example.team_7_case_8_product_management.model.item.Item;
import com.example.team_7_case_8_product_management.model.item.ItemDto;
import com.example.team_7_case_8_product_management.model.item.ItemSizeDto;
import com.example.team_7_case_8_product_management.model.warehouse.EmbeddedWarehouseId;
import com.example.team_7_case_8_product_management.model.warehouse.WarehouseEntity;
import com.example.team_7_case_8_product_management.model.warehouse.WarehouseStatus;
import com.example.team_7_case_8_product_management.repository.WarehouseDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static java.util.stream.Collectors.*;

@Service
@RequiredArgsConstructor
public class WarehouseService {

    private final WarehouseDao warehouseDao;

    public Iterable<WarehouseEntity> getAll() {
        return warehouseDao.findAll();
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void writeOffItem(ItemDto item, Long firstStatus, Long secondStatus) {
        Long itemId = item.getItemId();
        Set<ItemSizeDto> sizes = item.getSizes().stream()
                .filter(el -> el.getCount() > 0)
                .collect(toSet());
        Map<Long, Long> counts = new HashMap<>();
        for (ItemSizeDto size : sizes) {
            Long sizeId = size.getSizeId();
            Long count = size.getCount();
            counts.put(sizeId, count);
        }

        Set<Long> sizeIds = sizes.stream()
                .map(ItemSizeDto::getSizeId)
                .collect(toSet());
        Long statusId = 1l;

        Map<Long, Long> newCount = new HashMap<>();
        List<WarehouseEntity> allByItemIdStatusIdSizeId = warehouseDao.findAllByItemIdStatusIdSizeId(itemId, firstStatus, sizeIds);
        if (allByItemIdStatusIdSizeId.size() != sizeIds.size()) {
            throw new ItemNotFoundException();
        }

        List<WarehouseEntity> newWHEntities = new LinkedList<>();
        for (WarehouseEntity warehouseEntity : allByItemIdStatusIdSizeId) {
            Integer sizeId = warehouseEntity.getWarehouseId().getSize().getSizeId();

            Long count = warehouseEntity.getCount();
            Long noSaleCount = counts.get(sizeId);
            if (noSaleCount > count) {
                throw new ItemNotFoundException();
            }
            Long result = count - noSaleCount;
            warehouseEntity.setCount(result);
            Long newStatusId = 2l;

            WarehouseEntity we = WarehouseEntity.builder()
                    .count(result)
                    .warehouseId(EmbeddedWarehouseId.builder()
                            .itemId(Item.builder().itemId(itemId).build())
                            .status(WarehouseStatus.builder().statusId(secondStatus).build())
                            .size(SizeEntity.builder().sizeId(sizeId).build())
                            .build())
                    .build();
            newWHEntities.add(we);
        }

        warehouseDao.saveAll(allByItemIdStatusIdSizeId);
        warehouseDao.saveAll(newWHEntities);
    }
}
