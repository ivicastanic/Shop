package com.shop;


import com.shop.order.order_item.OrderItem;
import com.shop.order.order_item.service.OrderItemServiceLocal;

import java.util.List;

public class Demo {
    public static void main(String[] args) {

        List<OrderItem> customers= OrderItemServiceLocal.SERVICE.findAll();
        customers.forEach(c-> System.out.println(c));
    }
}
