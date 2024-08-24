package com.example.team_7_case_8_product_management.repository;

import com.example.team_7_case_8_product_management.model.order.OrderInfo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface OrderInfoDao extends CrudRepository<OrderInfo, Long> {

    @Query("select o from OrderInfo o where o.user.userId = ?1")
    Iterable<OrderInfo> findAllByUserId(Long userId);
    Optional<OrderInfo> findByOrderId(Long orderId);

}
