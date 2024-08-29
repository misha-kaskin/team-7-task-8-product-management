package com.example.team_7_case_8_product_management.service;

import com.example.team_7_case_8_product_management.exception.*;
import com.example.team_7_case_8_product_management.model.SizeEntity;
import com.example.team_7_case_8_product_management.model.cart.ExtendCartDto;
import com.example.team_7_case_8_product_management.model.user.User;
import com.example.team_7_case_8_product_management.model.cart.CartDto;
import com.example.team_7_case_8_product_management.model.cart.CartItemDto;
import com.example.team_7_case_8_product_management.model.cart.SizeDto;
import com.example.team_7_case_8_product_management.model.item.Item;
import com.example.team_7_case_8_product_management.model.order.*;
import com.example.team_7_case_8_product_management.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderInfoDao orderInfoDao;
    private final OrderDao orderDao;
    private final UserDao userDao;
    private final ItemDao itemDao;
    private final CartService cartService;
    private final FileStorage fileStorage;

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void addOrder(OrderDto orderDto) {
        Long userId = orderDto.getUserId();
        Optional<User> optionalUser = userDao.findById(userId);
        if (optionalUser.isEmpty()) {
            throw new UserNotFoundByIdException();
        }
        User user = optionalUser.get();
        ExtendCartDto userItems = cartService.getItemsByUserId(userId);
        Float amount = getTotalOrderAmount(userItems);
        if (user.getBalance() < amount) {
            throw new NotEnoughMoneyException();
        }
        Float newBalance = user.getBalance() - amount;
        user.setBalance(newBalance);
        userDao.save(user);

        OrderInfo orderInfo = OrderInfo.builder()
                .user(User.builder()
                        .userId(orderDto.getUserId())
                        .build())
                .status(OrderStatus.builder()
                        .statusId(orderDto.getStatusId())
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
        for (CartItemDto item : userItems.getItems()) {
            Long itemId = item.getItemId();
            for (SizeDto size : item.getSizes()) {
                Long sizeId = size.getSizeId();
                Long count = size.getCount();
                Order order = Order.builder()
                        .orderId(EmbeddedOrderId.builder()
                                .order(saveOrder)
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

        Collection<Order> orders1 = orderDao.checkWarehouseAvailable(orderId);
        if (userItems.getDeleted().size() > 0
                || userItems.getArchive().size() > 0
                || userItems.getExceed().size() > 0) {
            throw new TooManyItemsException(userItems);
        }
        orderDao.updateWarehouse(orderId);
        cartService.deleteAllById(userId);
    }

    public Iterable<ShortOrderDto> getAllOrders() {
        Iterable<OrderInfo> orders = orderInfoDao.findAll();
        return mapOrderCollection(orders);
    }

    public Collection<ShortOrderDto> getOrdersByUserId(Long userId) {
        Iterable<OrderInfo> orders = orderInfoDao.findAllByUserId(userId);
        return mapOrderCollection(orders);
    }

    public Collection<ShortOrderDto> getOrdersByDate(LocalDate date) {
        Iterable<OrderInfo> orders = orderInfoDao.findAllByDate(date);
        return mapOrderCollection(orders);
    }

    public ShortOrderDto getOrderById(Long id) {
        Collection<OrderInfo> orders = orderInfoDao.findAllByOrderId(id);
        if (orders.isEmpty()) {
            throw new OrderNotFoundException();
        } else {
            return mapOrderCollection(orders).iterator().next();
        }
    }

    private Float getTotalOrderAmount(ExtendCartDto orderDto) {
        Set<Long> itemIds = new HashSet<>();
        for (CartItemDto item : orderDto.getItems()) {
            itemIds.add(item.getItemId());
        }
        Collection<Item> items = itemDao.findAllByIds(itemIds);
        Map<Long, Item> itemMap = new HashMap<>();
        for (Item item : items) {
            itemMap.put(item.getItemId(), item);
        }

        float amount = 0f;
        for (CartItemDto item : orderDto.getItems()) {
            Float price = itemMap.get(item.getItemId()).getPrice();
            for (SizeDto size : item.getSizes()) {
                Long count = size.getCount();
                amount += count * price;
            }
        }
        return amount;
    }

    private Collection<ShortOrderDto> mapOrderCollection(Iterable<OrderInfo> orders) {
        Map<Long, ShortOrderDto> orderMap = new HashMap<>();
        for (OrderInfo order : orders) {
            Long orderId = order.getOrderId();
            ShortOrderDto orderDto = ShortOrderDto.builder()
                    .userId(order.getUser().getUserId())
                    .orderId(orderId)
                    .status(order.getStatus())
                    .orderDate(order.getOrderDate())
                    .firstName(order.getFirstName())
                    .secondName(order.getSecondName())
                    .lastName(order.getLastName())
                    .address(order.getAddress())
                    .items(new HashSet<>())
                    .build();
            orderMap.put(orderId, orderDto);
        }

        Collection<Long> orderIds = orderMap.keySet();
        Iterable<Item> items = orderDao.findAllItemsByOrderIds(orderIds);
        Map<Long, OrderItemDto> itemMap = new HashMap<>();
        for (Item item : items) {
            Long itemId = item.getItemId();
            String image = fileStorage.loadFile(item);

            OrderItemDto itemDto = OrderItemDto.builder()
                    .itemId(itemId)
                    .productName(item.getProductName())
                    .price(item.getPrice())
                    .image(image)
                    .build();
            itemMap.put(itemId, itemDto);
        }

        Iterable<SizeEntity> sizes = orderDao.findAllSizesByOrderIds(orderIds);
        Map<Long, OrderSizeDto> sizeMap = new HashMap<>();
        for (SizeEntity size : sizes) {
            Long sizeId = Long.valueOf(size.getSizeId());
            OrderSizeDto sizeDto = OrderSizeDto.builder()
                    .sizeId(sizeId)
                    .title(size.getTitle())
                    .build();
            sizeMap.put(sizeId, sizeDto);
        }

        Iterable<Object[]> orderIdItemIdSizeId = orderDao.findOrderIdsItemIdSizeIdCount(orderIds);
        Map<Long, OrderItemDto> orderItemMap = new HashMap<>();
        for (Object[] idArray : orderIdItemIdSizeId) {
            Long orderId = Long.parseLong(idArray[0].toString());
            Long itemId = Long.parseLong(idArray[1].toString());
            Long sizeId = Long.parseLong(idArray[2].toString());
            Long count = Long.parseLong(idArray[3].toString());

            ShortOrderDto orderDto = orderMap.get(orderId);
            OrderItemDto itemDto = itemMap.get(itemId);
            OrderSizeDto sizeDto = sizeMap.get(sizeId);

            if (orderDto.getItems().contains(itemDto)) {
                itemDto = orderItemMap.get(orderId);
            } else {
                itemDto = OrderItemDto.builder()
                        .itemId(itemDto.getItemId())
                        .productName(itemDto.getProductName())
                        .price(itemDto.getPrice())
                        .image(itemDto.getImage())
                        .sizes(new HashSet<>())
                        .build();
                orderDto.getItems().add(itemDto);
                orderItemMap.put(orderDto.getOrderId(), itemDto);
            }

            sizeDto = OrderSizeDto.builder()
                    .sizeId(sizeDto.getSizeId())
                    .title(sizeDto.getTitle())
                    .count(count)
                    .build();
            itemDto.getSizes().add(sizeDto);

        }

        return orderMap.values();
    }

}
