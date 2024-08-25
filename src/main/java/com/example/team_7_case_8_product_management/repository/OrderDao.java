package com.example.team_7_case_8_product_management.repository;

import com.example.team_7_case_8_product_management.model.SizeEntity;
import com.example.team_7_case_8_product_management.model.item.Item;
import com.example.team_7_case_8_product_management.model.order.Order;
import com.example.team_7_case_8_product_management.model.order.EmbeddedOrderId;
import com.example.team_7_case_8_product_management.model.order.OrderInfo;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface OrderDao extends CrudRepository<Order, EmbeddedOrderId> {

    @Query(
            "select o.orderId.order.orderId, o.orderId.item.itemId, o.orderId.size.sizeId, o.count " +
            "from Order o " +
            "where o.orderId.order.orderId in (?1)"
    )
    Iterable<Object[]> findOrderIdsItemIdSizeIdCount(Iterable<Long> ids);

    @Query(
            "select o.orderId.item " +
            "from Order o " +
            "where o.orderId.order.orderId in (?1)"
    )
    Iterable<Item> findAllItemsByOrderIds(Iterable<Long> ids);


    @Query(
            "select o.orderId.size " +
            "from Order o " +
            "where o.orderId.order.orderId in (?1)"
    )
    Iterable<SizeEntity> findAllSizesByOrderIds(Iterable<Long> ids);

    @Query(
            "select o.orderId.order " +
            "from Order o " +
            "where o.orderId.order.orderId in (?1)"
    )
    Iterable<OrderInfo> findAllOrdersByOrderIds(Iterable<Long> ids);


    @Query(
            "select o " +
            "from Order o " +
            "where o.orderId.order.orderId = ?1 " +
                    "and not exists (" +
                            "select wh " +
                            "from WarehouseEntity wh " +
                            "where o.orderId.item.itemId = wh.item.itemId " +
                                    "and o.orderId.size.sizeId = wh.size.sizeId " +
                                    "and o.count <= wh.count" +
                    ")"
    )
    Collection<Order> checkWarehouseAvailable(Long id);

    @Modifying
    @Query(
            nativeQuery = true,
//            value = "update warehouses as wh set count = wh.count - o.count " +
//                    "from orders as o " +
//                    "where wh.item_id = o.item_id " +
//                            "and wh.size_id = o.size_id " +
//                            "and wh.count <= o.count " +
//                            "and o.order_id = ?1"
            value = "update warehouses as wh " +
                    "set count = t.count " +
                    "from (" +
                            "select wh.id, wh.count - o.count as count " +
                            "from warehouses wh " +
                            "inner join orders o " +
                            "on wh.item_id = o.item_id " +
                                    "and wh.size_id = o.size_id " +
                                    "and wh.count <= o.count " +
                                    "and o.order_id = ?1" +
                    ") as t " +
                    "where wh.id = t.id"
    )
    void updateWarehouse(Long id);

}
