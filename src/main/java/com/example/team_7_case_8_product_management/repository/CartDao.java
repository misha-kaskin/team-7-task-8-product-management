package com.example.team_7_case_8_product_management.repository;

import com.example.team_7_case_8_product_management.model.SizeEntity;
import com.example.team_7_case_8_product_management.model.cart.Cart;
import com.example.team_7_case_8_product_management.model.cart.CartDto;
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
            "where cart.cartId.user.userId = ?1 "
    )
    Iterable<Object[]> getItemIdSizeId(Long id);

    @Query(
            nativeQuery = true,
            value =
            "select t1.item_id, t1.size_id, t1.count1, t1.count2 " +
            "from (select t.item_id, t.size_id, wh.count as count1, t.count as count2 "+
                    "from (select c.item_id, c.size_id, c.count " +
                    "from carts c " +
                    "where c.user_id = ?1 and not exists (" +
                            "select wh " +
                            "from warehouses wh " +
                            "where wh.item_id = c.item_id " +
                                    "and wh.size_id = c.size_id " +
                                    "and c.count <= wh.count " +
                                    "and wh.status_id = 1)) as t " +
            "join warehouses wh on t.size_id = wh.size_id and t.item_id = wh.item_id and wh.status_id = 1) as t1 " +
            "join items i on i.item_id = t1.item_id and i.state_id = 1"
    )
    List<Object[]> getExceedItems(Long id);

    @Query(
            "select c.cartId.item " +
            "from Cart c " +
            "where c.cartId.user.userId = ?1 " +
                    "and c.cartId.item.state.stateId = ?2"
    )
    List<Item> getArchiveItems(Long id, Long stateId);

    @Query(
            "select c " +
                    "from Cart c " +
                    "where c.cartId.user.userId = ?1 and exists (" +
                    "select wh " +
                    "from WarehouseEntity wh " +
                    "where wh.warehouseId.itemId.itemId = c.cartId.item.itemId " +
                    "and wh.warehouseId.size.sizeId = c.cartId.size.sizeId " +
                    "and c.count <= wh.count and wh.warehouseId.itemId.state.stateId = 1 and wh.warehouseId.status.statusId = 1)"
    )
    List<Cart> getAvailableItems(Long id);


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
