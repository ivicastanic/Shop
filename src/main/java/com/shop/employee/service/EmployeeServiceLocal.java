package com.shop.employee.service;

import com.shop.employee.Employee;

import java.util.List;

public interface EmployeeServiceLocal {
    EmployeeServiceLocal SERVICE = new EmployeeService();

    void create(Employee employee);

    void edit(Employee employee);

    void remove(Employee employee);

    void remove(Long id);

    Employee find(Long id);

    List<Employee> findAll();
}
