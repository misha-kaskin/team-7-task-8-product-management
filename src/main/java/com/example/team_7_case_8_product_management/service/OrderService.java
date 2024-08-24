package com.example.team_7_case_8_product_management.service;

import com.example.team_7_case_8_product_management.model.SizeEntity;
import com.example.team_7_case_8_product_management.model.User;
import com.example.team_7_case_8_product_management.model.item.Item;
import com.example.team_7_case_8_product_management.model.order.*;
import com.example.team_7_case_8_product_management.repository.OrderDao;
import com.example.team_7_case_8_product_management.repository.OrderInfoDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderInfoDao orderInfoDao;
    private final OrderDao orderDao;

    @Transactional
    public void addOrder(OrderDto orderDto) {
        OrderInfo orderInfo = OrderInfo.builder()
                .user(User.builder()
                        .userId(orderDto.getUserId())
                        .build())
                .status(OrderStatus.builder()
                        .orderId(orderDto.getStatusId())
                        .build())
                .orderDate(LocalDate.now())
                .firstName(orderDto.getFirstName())
                .secondName(orderDto.getSecondName())
                .lastName(orderDto.getLastName())
                .address(orderDto.getAddress())
                .build();

        OrderInfo saveOrder = orderInfoDao.save(orderInfo);
        Long orderId = saveOrder.getOrderId();
        List<Order> orders = new LinkedList<>();

        for (ShortOrderItemDto item : orderDto.getItems()) {
            Long itemId = item.getItemId();
            for (ShortOrderSizeDto size : item.getSizes()) {
                Long sizeId = size.getSizeId();
                Long count = size.getCount();
                Order order = Order.builder()
                        .orderId(OrderId.builder()
                                .order(OrderInfo.builder()
                                        .orderId(orderId)
                                        .build())
                                .item(Item.builder()
                                        .itemId(itemId)
                                        .build())
                                .size(SizeEntity.builder()
                                        .sizeId(Math.toIntExact(sizeId))
                                        .build())
                                .build())
                        .count(count)
                        .build();
                orders.add(order);
            }
        }

        orderDao.saveAll(orders);
    }

}
