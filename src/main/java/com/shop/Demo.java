package com.shop;

import com.shop.employee.Employee;
import com.shop.employee.privilege.Privilege;
import com.shop.employee.privilege.service.PrivilegeServiceLocal;
import com.shop.employee.service.EmployeeServiceLocal;
import com.shop.product.Product;
import com.shop.product.service.ProductServiceLocal;


import java.util.List;

public class Demo {
    public static void main(String[] args) {

        List<Employee> productList= EmployeeServiceLocal.SERVICE.findAll();
        productList.forEach(System.out::println);
    }
}
