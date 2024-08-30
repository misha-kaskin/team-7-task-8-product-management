package com.example.team_7_case_8_product_management.service;

import com.example.team_7_case_8_product_management.controller_advice.Marker;
import com.example.team_7_case_8_product_management.exception.CartUserIdMismatchException;
import com.example.team_7_case_8_product_management.exception.ItemNotFoundException;
import com.example.team_7_case_8_product_management.exception.TooManyItemsException;
import com.example.team_7_case_8_product_management.model.SizeEntity;
import com.example.team_7_case_8_product_management.model.cart.*;
import com.example.team_7_case_8_product_management.model.item.*;
import com.example.team_7_case_8_product_management.model.user.User;
import com.example.team_7_case_8_product_management.repository.CartDao;
import com.example.team_7_case_8_product_management.repository.FileStorage;
import com.example.team_7_case_8_product_management.repository.ItemDao;
import com.example.team_7_case_8_product_management.repository.SizeDao;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

@Service
@RequiredArgsConstructor
public class CartService {

    @Value("${file.storage.path}")
    private String path;

    private final CartDao cartDao;
    private final ItemDao itemDao;
    private final SizeDao sizeDao;
    private final FileStorage fileStorage;

    @Transactional
    public void addItemToCart(CartDto cartDto, Long userId) {
        if (!Objects.equals(cartDto.getUserId(), userId)) {
            throw new CartUserIdMismatchException();
        }

        System.out.println(cartDto);
        List<Cart> cartList = new LinkedList<>();
        for (CartItemDto item : cartDto.getItems()) {
            for (SizeDto size : item.getSizes()) {
                Cart cart = Cart.builder()
                        .cartId(EmbeddedCartId.builder()
                                .user(User.builder()
                                        .userId(userId)
                                        .build())
                                .item(Item.builder()
                                        .itemId(item.getItemId())
                                        .build())
                                .size(SizeEntity.builder()
                                        .sizeId(Math.toIntExact(size.getSizeId()))
                                        .build())
                                .build())
                        .count(size.getCount())
                        .build();
                cartList.add(cart);
            }
        }

        cartDao.saveAll(cartList);

        ExtendCartDto extendCartDto = getItemsByUserId(userId);
        if (extendCartDto.getExceed().size() > 0
                || extendCartDto.getArchive().size() > 0
                || extendCartDto.getDeleted().size() > 0) {
            throw new TooManyItemsException(extendCartDto);
        }
    }

    public Iterable<Cart> getAllCarts() {
        return cartDao.findAll();
    }

    public ExtendCartDto getItemsByUserId(Long id) {
        List<Object[]> itemsList = cartDao.getExceedItems(id);
        Set<Long> itemIds = new HashSet<>();
        Set<Long> sizeIds = new HashSet<>();
        for (Object[] objects : itemsList) {
            Long itemId = Long.parseLong(objects[0].toString());
            Long sizeId = Long.parseLong(objects[1].toString());
            itemIds.add(itemId);
            sizeIds.add(sizeId);
        }

        Collection<Item> items = itemDao.findAllByIds(itemIds);
        Iterable<SizeEntity> sizes = sizeDao.findAll();
        Map<Long, ExtendItemDto> itemMap = new HashMap<>();
        Map<Long, SizeEntity> sizeMap = new HashMap<>();

        for (Item item : items) {
            Long itemId = item.getItemId();
            ExtendItemDto itemDto = ExtendItemDto.builder()
                    .itemId(itemId)
                    .type(item.getType())
                    .productName(item.getProductName())
                    .description(item.getDescription())
                    .price(item.getPrice())
//                    .image(fileStorage.loadFile(item))
                    .sizes(new HashSet<>())
                    .build();
            itemMap.put(itemId, itemDto);
        }

        for (SizeEntity size : sizes) {
            Long sizeId = Long.valueOf(size.getSizeId());
            sizeMap.put(sizeId, size);
        }

        for (Object[] objects : itemsList) {
            Long itemId = Long.parseLong(objects[0].toString());
            Long sizeId = Long.parseLong(objects[1].toString());
            Long whCount = Long.parseLong(objects[2].toString());
            Long cartCount = Long.parseLong(objects[3].toString());

            SizeEntity size = sizeMap.get(sizeId);
            ExtendSizeDto sizeDto = ExtendSizeDto.builder()
                    .sizeId(sizeId)
                    .title(size.getTitle())
                    .whCount(whCount)
                    .cartCount(cartCount)
                    .build();

            ExtendItemDto itemDto = itemMap.get(itemId);
            itemDto.getSizes().add(sizeDto);
        }

        Collection<ExtendItemDto> exceed = itemMap.values();

        Collection<Cart> carts = cartDao.getAvailableItems(id);
        Set<Long> newItemIds = carts.stream()
                .map(el -> el.getCartId().getItem().getItemId())
                .collect(toSet());

        Collection<Item> allByIds = itemDao.findAllByIds(newItemIds);
        Map<Long, CartItemDto> cartMap = new HashMap<>();
        for (Item item : allByIds) {
            CartItemDto itemDto = CartItemDto.builder()
                    .itemId(item.getItemId())
                    .productName(item.getProductName())
                    .price(item.getPrice())
                    .image(fileStorage.loadFile(item))
                    .sizes(new HashSet<>())
                    .build();
            cartMap.put(item.getItemId(), itemDto);
        }

        for (Cart cart : carts) {
            Item item = cart.getCartId().getItem();
            SizeEntity size = cart.getCartId().getSize();
            Long count = cart.getCount();
            Long itemId = item.getItemId();
            CartItemDto itemDto = cartMap.get(itemId);

            SizeDto sizeDto = SizeDto.builder()
                    .sizeId(Long.valueOf(size.getSizeId()))
                    .title(size.getTitle())
                    .count(count)
                    .build();
            itemDto.getSizes().add(sizeDto);
        }

        Set<FullItemDto> archive = cartDao.getArchiveItems(id, 2l).stream()
                .map(el -> FullItemDto.builder()
                        .itemId(el.getItemId())
                        .type(el.getType())
                        .productName(el.getProductName())
                        .description(el.getDescription())
                        .price(el.getPrice())
                        .image(fileStorage.loadFile(el))
                        .sizes(new HashSet<>())
                        .build()
                )
                .collect(toSet());



        Set<FullItemDto> deleted = cartDao.getArchiveItems(id, 3l).stream()
                .map(el -> FullItemDto.builder()
                        .itemId(el.getItemId())
                        .type(el.getType())
                        .productName(el.getProductName())
                        .description(el.getDescription())
                        .price(el.getPrice())
                        .image(fileStorage.loadFile(el))
                        .sizes(new HashSet<>())
                        .build()
                )
                .collect(toSet());

        System.out.println(deleted);

        return ExtendCartDto.builder()
                .userId(id)
                .exceed(exceed)
                .archive(archive)
                .deleted(deleted)
                .items(cartMap.values())
                .build();
    }

    private CartDto mapToCartDto(Long id, Iterable<Item> items, Iterable<SizeEntity> sizes, Iterable<Object[]> itemIdSizeId) {
        Map<Long, CartItemDto> itemMap = new HashMap<>();
        for (Item item : items) {
            Long itemId = item.getItemId();
            String productName = item.getProductName();
            Float price = item.getPrice();
            String image = fileStorage.loadFile(item);

            CartItemDto itemDto = CartItemDto.builder()
                    .itemId(itemId)
                    .productName(productName)
                    .price(price)
                    .sizes(new HashSet<>())
                    .image(image)
                    .build();
            itemMap.put(itemId, itemDto);
        }

        Map<Long, SizeEntity> sizeMap = new HashMap<>();
        for (SizeEntity size : sizes) {
            Integer sizeId = size.getSizeId();
            sizeMap.put(Long.valueOf(sizeId), size);
        }

        for (Object[] itemIdSizeIdCount : itemIdSizeId) {
            Long itemId = Long.parseLong(itemIdSizeIdCount[0].toString());
            Long sizeId = Long.parseLong(itemIdSizeIdCount[1].toString());
            Long count = Long.parseLong(itemIdSizeIdCount[2].toString());

            CartItemDto itemDto = itemMap.get(itemId);
            SizeEntity size = sizeMap.get(sizeId);
            SizeDto sizeDto = SizeDto.builder()
                    .sizeId(sizeId)
                    .title(size.getTitle())
                    .count(count)
                    .build();

            itemDto.getSizes().add(sizeDto);
        }

        return CartDto.builder()
                .userId(id)
                .items(itemMap.values())
                .build();
    }

    @Transactional
    public void deleteAllById(Long userId) {
        cartDao.deleteAllByUserId(userId);
    }

    @Transactional
    public void deleteById(Long userId, Long itemId, Long sizeId) {
        if (cartDao.findAllById(userId, itemId, sizeId).isEmpty()) {
            throw new ItemNotFoundException();
        }
        cartDao.deleteAllById(userId, itemId, sizeId);
    }
}
