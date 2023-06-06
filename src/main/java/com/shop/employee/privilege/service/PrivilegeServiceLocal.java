package com.shop.employee.privilege.service;

import com.shop.employee.privilege.Privilege;
import com.shop.product.Product;


import java.util.List;

public interface PrivilegeServiceLocal {
    PrivilegeServiceLocal SERVICE=new PrivilegeService();

    void create(Privilege privilege);

    void edit(Privilege privilege);

    void remove(Privilege privilege);
    void remove(Long id);

    Privilege find(Long id);

    List<Privilege> findAll();
}
