package com.shop.order.service;

import com.shop.order.Order;
import com.shop.order.order_status.OrderStatus;

import java.time.LocalDate;
import java.util.List;

public interface OrderServiceLocal {
    OrderServiceLocal SERVICE=new OrderService();

    void create(Order order);

    void edit(Order order);

    void remove(Order order);

    void remove(Long id);

    Order find(Long id);

    List<Order> findAll();
    List<Order> findByDate(LocalDate date);
    public List<OrderStatus> findAllOrderStatus();
}
