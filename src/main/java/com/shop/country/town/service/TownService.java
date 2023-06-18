package com.shop.country.town.service;

import com.shop.country.Country;
import com.shop.country.town.Town;
import com.shop.service.AbstractService;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;

import java.util.Collections;
import java.util.List;

class TownService extends AbstractService<Town> implements TownServiceLocal {
    public TownService() {
        super(Town.class);
    }

    @Override
    public List<Town> findByCountry(Country country) {
        Query query= getEntityManager().createNamedQuery("Town.findByCountry");
        query.setParameter("country", country);
        return query.getResultList();
    }
    @Override
    public Town findByName(String name) {
        try {
            Query query =getEntityManager().createNamedQuery("Town.findByName");
            query.setParameter("name", name);
            return (Town) query.getSingleResult();
        }catch (NoResultException exception){
            throw  exception;
        }
    }
}
