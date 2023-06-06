package com.shop.employee.service;

import com.shop.employee.Employee;
import com.shop.service.AbstractService;

class EmployeeService extends AbstractService<Employee> implements EmployeeServiceLocal{
    public EmployeeService() {
        super(Employee.class);
    }
}
