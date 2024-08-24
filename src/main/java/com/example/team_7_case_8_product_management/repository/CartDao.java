package com.example.team_7_case_8_product_management.repository;

import com.example.team_7_case_8_product_management.model.SizeEntity;
import com.example.team_7_case_8_product_management.model.cart.Cart;
import com.example.team_7_case_8_product_management.model.item.Item;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

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
            "where not exists (" +
                    "select wh.id " +
                    "from WarehouseEntity wh " +
                    "where wh.item.itemId = c.cartId.item.itemId " +
                            "and wh.size.sizeId = c.cartId.size.sizeId " +
                            "and c.count <= wh.count)"
    )
    List<Object> getItems(Long id);

}
