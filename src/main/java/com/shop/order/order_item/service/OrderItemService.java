package com.shop.order.order_item.service;

import com.shop.order.order_item.OrderItem;
import com.shop.service.AbstractService;

class OrderItemService extends AbstractService<OrderItem> implements OrderItemServiceLocal {
    public OrderItemService() {
        super(OrderItem.class);
    }
}
