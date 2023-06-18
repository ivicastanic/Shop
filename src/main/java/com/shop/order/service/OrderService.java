package com.shop.order.service;

import com.shop.employee.Employee;
import com.shop.order.Order;
import com.shop.order.order_status.OrderStatus;
import com.shop.service.AbstractService;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;

import java.time.LocalDate;
import java.util.List;

class OrderService extends AbstractService<Order> implements OrderServiceLocal{
    public OrderService() {
        super(Order.class);
    }

    @Override
    public List<Order> findByDate(LocalDate date) {
        try {
            Query query =getEntityManager().createNamedQuery("Order.findByDate");
            query.setParameter("date", date);
            return query.getResultList();
        }catch (NoResultException exception){
            throw  exception;
        }
    }

    @Override
    public List<OrderStatus> findAllOrderStatus() {
        Query query=getEntityManager().createNamedQuery("OrderStatus.findAll");
        return query.getResultList();
    }
}
