package com.shop.employee.privilege.service;

import com.shop.employee.privilege.Privilege;
import com.shop.service.AbstractService;

class PrivilegeService extends AbstractService<Privilege> implements PrivilegeServiceLocal {
    public PrivilegeService() {
        super(Privilege.class);
    }
}
