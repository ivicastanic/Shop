package com.shop.country.service;

import com.shop.country.Country;
import com.shop.employee.Employee;
import com.shop.service.AbstractService;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;

class CountryService extends AbstractService<Country> implements CountryServiceLocal {
    public CountryService() {
        super(Country.class);
    }

    @Override
    public Country findByName(String name) {
        try {
            Query query =getEntityManager().createNamedQuery("Country.findByName");
            query.setParameter("name", name);
            return (Country) query.getSingleResult();
        }catch (NoResultException exception){
            throw  exception;
        }
    }
}
