package com.example.team_7_case_8_product_management.service;

import com.example.team_7_case_8_product_management.exception.CantFindFileException;
import com.example.team_7_case_8_product_management.exception.CartUserIdMismatchException;
import com.example.team_7_case_8_product_management.exception.TooManyItemsException;
import com.example.team_7_case_8_product_management.model.SizeEntity;
import com.example.team_7_case_8_product_management.model.user.User;
import com.example.team_7_case_8_product_management.model.cart.*;
import com.example.team_7_case_8_product_management.model.item.Item;
import com.example.team_7_case_8_product_management.repository.CartDao;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class CartService {

    @Value("${file.storage.path}")
    private String path;

    private final CartDao cartDao;

    @Transactional
    public void addItemToCart(CartDto cartDto, Long userId) {
        if (!Objects.equals(cartDto.getUserId(), userId)) {
            throw new CartUserIdMismatchException();
        }

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

        List<Object> items = cartDao.getItems(userId);
        if (items.size() > 0) {
            throw new TooManyItemsException();
        }
    }

    public Iterable<Cart> getAllCarts() {
        return cartDao.findAll();
    }

    public CartDto getItemsByUserId(Long id) {
        List<Object> itemsList = cartDao.getItems(id);
        if (itemsList.size() > 0) {
            throw new TooManyItemsException();
        }

        Iterable<Item> items = cartDao.findAllItemsByUserId(id);
        Iterable<SizeEntity> sizes = cartDao.findAllSizesByUserId(id);
        Iterable<Object[]> itemIdSizeId = cartDao.getItemIdSizeId(id);
        return mapToCartDto(id, items, sizes, itemIdSizeId);
    }

    private CartDto mapToCartDto(Long id, Iterable<Item> items, Iterable<SizeEntity> sizes, Iterable<Object[]> itemIdSizeId) {
        Map<Long, CartItemDto> itemMap = new HashMap<>();
        for (Item item : items) {
            Long itemId = item.getItemId();
            String productName = item.getProductName();
            Float price = item.getPrice();
            String fileName = item.getType() + item.getProductName() + item.getItemId();
            String image;
            try (FileInputStream fis = new FileInputStream(path + fileName)) {
                byte[] bytes = fis.readAllBytes();
                image = new String(bytes);
            } catch (IOException e) {
                throw new CantFindFileException();
            }

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

    public void deleteAllById(Long userId) {
        cartDao.deleteAll();
    }
}
