package com.shop.country.service;

import com.shop.country.Country;
import com.shop.service.AbstractService;

class CountryService extends AbstractService<Country> implements CountryServiceLocal {
    public CountryService() {
        super(Country.class);
    }
}
