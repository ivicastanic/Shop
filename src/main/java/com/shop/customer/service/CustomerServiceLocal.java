package com.shop.customer.service;

import com.shop.customer.Customer;
import com.shop.employee.Employee;
import javafx.collections.ObservableList;


import java.util.List;

public interface CustomerServiceLocal {
    CustomerServiceLocal SERVICE = new CustomerService();

    void create(Customer customer);

    void edit(Customer customer);

    void remove(Customer customer);

    void remove(Long id);

    Customer find(Long id);

    List<Customer> findAll();
    ObservableList<Customer> loadCustomers();
}
