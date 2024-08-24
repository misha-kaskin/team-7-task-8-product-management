package com.example.team_7_case_8_product_management.repository;

import com.example.team_7_case_8_product_management.model.item.Item;
import com.example.team_7_case_8_product_management.model.order.Order;
import com.example.team_7_case_8_product_management.model.order.OrderId;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface OrderDao extends CrudRepository<Order, OrderId> {

    @Query("select o from Order o where o.orderId.order.orderId = ?1")
    Iterable<Order> findAllById(Long id);

    @Query("select o from Order  o where o.orderId.order.orderId in (?1)")
    Iterable<Order> findAllByOrderId(Iterable<Long> ids);

}
