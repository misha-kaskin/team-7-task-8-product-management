package com.example.team_7_case_8_product_management.service;

import com.example.team_7_case_8_product_management.exception.ItemNotFoundException;
import com.example.team_7_case_8_product_management.model.SizeEntity;
import com.example.team_7_case_8_product_management.model.item.*;
import com.example.team_7_case_8_product_management.model.warehouse.EmbeddedWarehouseId;
import com.example.team_7_case_8_product_management.model.warehouse.WarehouseEntity;
import com.example.team_7_case_8_product_management.model.warehouse.WarehouseStatus;
import com.example.team_7_case_8_product_management.repository.FileStorage;
import com.example.team_7_case_8_product_management.repository.ItemDao;
import com.example.team_7_case_8_product_management.repository.WarehouseDao;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemService {

    @Value("${file.storage.path}")
    private String path;
    private final WarehouseDao warehouseDao;
    private final ItemDao itemDao;
    private final FileStorage fileStorage;


    @Transactional
    public void addItem(FullItemDto itemDto) {
        Item item = mapToItem(itemDto);
        item.setState(ItemState.builder().stateId(1l).build());
        item = itemDao.save(item);
        itemDto.setItemId(item.getItemId());
        List<WarehouseEntity> warehouseEntities = mapToListWarehouseEntity(item, itemDto, 1l);
        warehouseDao.saveAll(warehouseEntities);
        fileStorage.saveFile(itemDto);
    }

    public void deleteItem(FullItemDto itemDto) {
        Long id = itemDto.getItemId();
        Optional<Item> optionalItem = itemDao.findById(id);
        if (optionalItem.isEmpty()) {
            throw new ItemNotFoundException();
        }
        Item item = optionalItem.get();
        if (item.getState().getStateId() == 3l) {
            throw new ItemNotFoundException();
        }
        item.setState(ItemState.builder().stateId(3l).build());
        itemDao.save(item);
    }

    public Collection<ShortItemDto> getSaleItems() {
        return warehouseDao.findAllByStatus(1l, 1l)
                .stream().map(item -> {
                    ShortItemDto itemDto = ShortItemDto.builder()
                            .itemId(item.getItemId())
                            .productName(item.getProductName())
                            .typeId(item.getType().getTypeId())
                            .price(item.getPrice())
                            .build();
                    String image = fileStorage.loadFile(item);
                    itemDto.setImage(image);
                    return itemDto;
                })
                .collect(Collectors.toList());
    }

    public FullItemDto getFullItemDtoById(Long id, Long statusId) {
        Optional<Item> optionalItem = itemDao.findById(id);
        if (optionalItem.isEmpty()) {
            throw new ItemNotFoundException();
        }

        List<WarehouseEntity> itemList = warehouseDao.findAllSaleByItemId(id, statusId, 1l);
        Item item = optionalItem.get();
        Set<ItemSizeDto> sizes = new HashSet<>();
        for (WarehouseEntity warehouseEntity : itemList) {
            SizeEntity size1 = warehouseEntity.getWarehouseId().getSize();
            Long count = warehouseEntity.getCount();
            ItemSizeDto size = ItemSizeDto.builder()
                    .sizeId(Long.valueOf(size1.getSizeId()))
                    .count(count)
                    .title(size1.getTitle())
                    .build();
            sizes.add(size);
        }
        FullItemDto itemDto = mapItemToItemDto(item);
        itemDto.setSizes(sizes);
        String image = fileStorage.loadFile(item);
        itemDto.setImage(image);
        return itemDto;
    }

    private FullItemDto mapItemToItemDto(Item item) {
        return FullItemDto.builder()
                .type(item.getType())
                .itemId(item.getItemId())
                .description(item.getDescription())
                .price(item.getPrice())
                .productName(item.getProductName())
                .sizes(new HashSet<>())
                .build();
    }

    private List<WarehouseEntity> mapToListWarehouseEntity(Item item, FullItemDto itemDto, Long statusId) {
        List<WarehouseEntity> warehouseEntities = new LinkedList<>();

        for (ItemSizeDto size : itemDto.getSizes()) {
            WarehouseEntity wh = WarehouseEntity.builder()
                    .warehouseId(EmbeddedWarehouseId.builder()
                            .itemId(item)
                            .size(SizeEntity.builder()
                                    .sizeId(Math.toIntExact(size.getSizeId()))
                                    .build())
                            .status(WarehouseStatus.builder()
                                    .statusId(statusId)
                                    .build())
                            .build())
                    .count(size.getCount())
                    .build();
            warehouseEntities.add(wh);
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
    public void updateItem(FullItemDto itemDto, Long statusId) {
        System.out.println(itemDto);
        Item item = mapToItem(itemDto);
        item.setState(ItemState.builder().stateId(1l).build());
        itemDao.save(item);
        List<WarehouseEntity> list = mapToListWarehouseEntity(item, itemDto, statusId);
        warehouseDao.saveAll(list);
        fileStorage.saveFile(itemDto);
    }

    public void archiveItem(Long id) {
        Optional<Item> optionalItem = itemDao.findById(id);
        if (optionalItem.isEmpty()) {
            throw new ItemNotFoundException();
        }
        Item item = optionalItem.get();
        if (item.getState().getStateId() == 3l) {
            throw new ItemNotFoundException();
        }
        itemDao.archiveItemById(id);
    }

    public ManagerItems getManagerItems() {
        List<Item> actual = warehouseDao.findAllByStatus(1l, 1l);
        List<Item> noSale = warehouseDao.findAllByStatus(2l, 1l);
//        itemDao.findAllByStateId(2l);

        //TODO
        return null;
    }
}
