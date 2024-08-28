package com.example.team_7_case_8_product_management.repository;

import com.example.team_7_case_8_product_management.model.SizeEntity;
import com.example.team_7_case_8_product_management.model.cart.Cart;
import com.example.team_7_case_8_product_management.model.item.Item;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CartDao extends CrudRepository<Cart, Long> {

    @Query(
            "select cart "
                    + "from Cart cart "
                    + "where cart.cartId.user.userId = ?1"
    )
    Iterable<Cart> findAllByUserId(Long id);

    @Query(
            "select cart.cartId.item "
                    + "from Cart cart "
                    + "where cart.cartId.user.userId = ?1"
    )
    Iterable<Item> findAllItemsByUserId(Long id);

    @Query(
            "select cart.cartId.size "
                    + "from Cart cart "
                    + "where cart.cartId.user.userId = ?1"
    )
    Iterable<SizeEntity> findAllSizesByUserId(Long id);

    @Query(
            "select cart.cartId.item.itemId, cart.cartId.size.sizeId, cart.count " +
            "from Cart cart " +
            "where cart.cartId.user.userId = ?1"
    )
    Iterable<Object[]> getItemIdSizeId(Long id);

    @Query(
            "select c.cartId.item " +
            "from Cart c " +
            "where c.cartId.user.userId = ?1 and not exists (" +
                    "select wh " +
                    "from WarehouseEntity wh " +
                    "where wh.warehouseId.itemId.itemId = c.cartId.item.itemId " +
                            "and wh.warehouseId.size.sizeId = c.cartId.size.sizeId " +
                            "and c.count <= wh.count and wh.warehouseId.itemId.state.stateId = 1 and wh.warehouseId.status.statusId = 1)"
    )
    List<Object> getItems(Long id);

    @Modifying
    @Query("delete from Cart c where c.cartId.user.userId = ?1")
    void deleteAllByUserId(Long userId);

    @Modifying
    @Query(
            nativeQuery = true,
            value = "delete from carts " +
            "where user_id = ?1 " +
                    "and item_id = ?2 " +
                    "and size_id = ?3"
    )
    void deleteAllById(Long userId, Long itemId, Long sizeId);

    @Query("select c from Cart c where c.cartId.user.userId = ?1 and c.cartId.item.itemId = ?2 and c.cartId.size.sizeId = ?3")
    Optional<Cart> findAllById(Long userId, Long itemId, Long sizeId);

}
