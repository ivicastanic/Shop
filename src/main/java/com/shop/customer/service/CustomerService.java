package com.shop.customer.service;

import com.shop.customer.Customer;
import com.shop.service.AbstractService;

class CustomerService extends AbstractService<Customer> implements CustomerServiceLocal{
    public CustomerService() {
        super(Customer.class);
    }
}
