package com.shop.employee.service;

import com.shop.employee.Employee;
import com.shop.service.AbstractService;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

class EmployeeService extends AbstractService<Employee> implements EmployeeServiceLocal{
    public EmployeeService() {
        super(Employee.class);
    }

    @Override
    public Employee findbyUsername(String username) {
        try {
            Query query =getEntityManager().createNamedQuery("Employee.findByUsername");
            query.setParameter("username", username);
            return (Employee) query.getSingleResult();
        }catch (NoResultException exception){
            throw  exception;
        }
    }

    @Override
    public ObservableList<Employee> loadEmployee() {
        List<Employee> employees=findAll();
        return FXCollections.observableList(employees);
    }
}
