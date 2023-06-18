package com.shop.country.town.address.service;

import com.shop.country.town.address.Address;
import com.shop.employee.Employee;
import com.shop.service.AbstractService;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;

class AddressService extends AbstractService<Address> implements AddressServiceLocal {
    public AddressService() {
        super(Address.class);
    }

    @Override
    public Address findByName(String name) {
        try {
            Query query =getEntityManager().createNamedQuery("Address.findByName");
            query.setParameter("name", name);
            return (Address) query.getSingleResult();
        }catch (NoResultException exception){
            throw  exception;
        }
    }
}
