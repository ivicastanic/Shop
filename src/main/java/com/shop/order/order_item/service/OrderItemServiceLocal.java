package com.shop.order.order_item.service;


import com.shop.order.order_item.OrderItem;

import java.util.List;

public interface OrderItemServiceLocal {
    OrderItemServiceLocal SERVICE=new OrderItemService();

    void create(OrderItem orderItem);

    void edit(OrderItem orderItem);

    void remove(OrderItem orderItem);

    void remove(Long id);

    OrderItem find(Long id);

    List<OrderItem> findAll();
}
