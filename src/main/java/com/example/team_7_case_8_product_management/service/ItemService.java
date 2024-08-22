package com.example.team_7_case_8_product_management.service;

import com.example.team_7_case_8_product_management.exception.CantCreateFileException;
import com.example.team_7_case_8_product_management.exception.CantFindFileException;
import com.example.team_7_case_8_product_management.exception.CantRemoveFileException;
import com.example.team_7_case_8_product_management.exception.ItemNotFoundException;
import com.example.team_7_case_8_product_management.model.*;
import com.example.team_7_case_8_product_management.repository.ItemDao;
import com.example.team_7_case_8_product_management.repository.SizeDao;
import com.example.team_7_case_8_product_management.repository.WarehouseDao;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemService {

    @Value("${file.storage.path}")
    private String path;
    private final WarehouseDao warehouseDao;
    private final ItemDao itemDao;
    private final SizeDao sizeDao;


    @Transactional
    public void addItem(FullItemDto itemDto) {
        Item item = mapToItem(itemDto);
        item = itemDao.save(item);
        List<WarehouseEntity> warehouseEntities = mapToListWarehouseEntity(item, itemDto);
        warehouseDao.saveAll(warehouseEntities);
        String file = itemDto.getImage();
        String fileName = item.getType() + item.getProductName() + item.getItemId();
        try (FileOutputStream fos = new FileOutputStream(path + fileName)) {
            fos.write(file.getBytes());
        } catch (IOException e) {
            throw new CantCreateFileException();
        }
    }

    @Transactional
    public void deleteItem(FullItemDto itemDto) {
        Long id = itemDto.getItemId();
        Item item = Item.builder()
                .itemId(id)
                .build();
        Optional<Item> optionalItem = itemDao.findById(id);
        if (warehouseDao.existsByItem(item) && optionalItem.isPresent()) {
            Item existsItem = optionalItem.get();
            warehouseDao.deleteAllByItem(item);
            itemDao.deleteByItemId(item.getItemId());
            String fileName = existsItem.getType() + existsItem.getProductName() + existsItem.getItemId();
            File file = new File(path + fileName);
            if (!file.delete()) {
                throw new CantRemoveFileException();
            }
        } else {
            throw new ItemNotFoundException();
        }
    }

    public Collection<ShortItemDto> getSaleItems() {
        return warehouseDao.findAllByStatus("SALE")
                .stream().map(item -> {
                    ShortItemDto itemDto = ShortItemDto.builder()
                            .itemId(item.getItemId())
                            .productName(item.getProductName())
                            .type(item.getType())
                            .price(item.getPrice())
                            .build();
                    String fileName = item.getType() + item.getProductName() + item.getItemId();
                    try (FileInputStream fis = new FileInputStream(path + fileName)) {
                        byte[] bytes = fis.readAllBytes();
                        String image = new String(bytes);
                        itemDto.setImage(image);
                    } catch (IOException e) {
                        throw new CantFindFileException();
                    }
                    return itemDto;
                })
                .collect(Collectors.toList());
    }

    public FullItemDto getFullItemDtoById(Long id) {
        List<WarehouseEntity> itemList = warehouseDao.findByItem(id, "SALE");
        if (itemList.isEmpty()) {
            throw new ItemNotFoundException();
        }
        Item item = itemList.get(0).getItem();
        long[] sizes = new long[7];
        for (WarehouseEntity warehouseEntity : itemList) {
            Integer sizeId = warehouseEntity.getSize().getSizeId();
            sizes[sizeId-1] = warehouseEntity.getCount();
        }
        FullItemDto itemDto = mapItemToItemDto(item);
        itemDto.setSizes(sizes);
        String fileName = itemDto.getType() + itemDto.getProductName() + itemDto.getItemId();
        try (FileInputStream fis = new FileInputStream(path + fileName)) {
            byte[] bytes = fis.readAllBytes();
            String image = new String(bytes);
            itemDto.setImage(image);
        } catch (IOException e) {
            throw new CantFindFileException();
        }
        return itemDto;
    }

    private FullItemDto mapItemToItemDto(Item item) {
        return FullItemDto.builder()
                .itemId(item.getItemId())
                .description(item.getDescription())
                .price(item.getPrice())
                .productName(item.getProductName())
                .sizes(new long[7])
                .build();
    }

    private List<WarehouseEntity> mapToListWarehouseEntity(Item item, FullItemDto itemDto) {
        Iterable<SizeEntity> allSizes = sizeDao.findAll();
        long[] arr = itemDto.getSizes();
        List<WarehouseEntity> warehouseEntities = new ArrayList<>();
        long cnt = 1;
        for (SizeEntity size : allSizes) {
            int id = size.getSizeId();
            WarehouseEntity whEntity = WarehouseEntity.builder()
                    .count(arr[id - 1])
                    .item(item)
                    .size(size)
                    .status("SALE")
                    .id(cnt++)
                    .build();
            warehouseEntities.add(whEntity);
        }
        return warehouseEntities;
    }

    private Item mapToItem(FullItemDto itemDto) {
        return Item.builder()
                .itemId(itemDto.getItemId())
                .description(itemDto.getDescription())
                .price(itemDto.getPrice())
                .type(itemDto.getType())
                .productName(itemDto.getProductName())
                .build();
    }

    public Iterable<Item> getAllItems() {
        return itemDao.findAll();
    }

    @Transactional
    public void updateItem(FullItemDto itemDto) {
        Item item = mapToItem(itemDto);
        itemDao.save(item);
        List<WarehouseEntity> list = mapToListWarehouseEntity(item, itemDto);
        warehouseDao.saveAll(list);
        String fileName = item.getType() + item.getProductName() + item.getItemId();
        try (FileOutputStream fos = new FileOutputStream(path + fileName)) {
            fos.write(itemDto.getImage().getBytes());
        } catch (IOException e) {
            throw new CantCreateFileException();
        }
    }

}
