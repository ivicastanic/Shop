package com.shop.order.service;

import com.shop.order.Order;
import com.shop.service.AbstractService;

class OrderService extends AbstractService<Order> implements OrderServiceLocal{
    public OrderService() {
        super(Order.class);
    }
}
