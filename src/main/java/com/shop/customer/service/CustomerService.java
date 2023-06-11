package com.shop.customer.service;

import com.shop.customer.Customer;
import com.shop.employee.Employee;
import com.shop.service.AbstractService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

class CustomerService extends AbstractService<Customer> implements CustomerServiceLocal{
    public CustomerService() {
        super(Customer.class);
    }

    @Override
    public ObservableList<Customer> loadCustomers() {
            List<Customer> customers=findAll();
            return FXCollections.observableList(customers);

    }
}
