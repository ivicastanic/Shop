package com.shop.country.service;

import com.shop.country.Country;
import java.util.List;

public interface CountryServiceLocal {
    CountryServiceLocal SERVICE = new CountryService();

    void create(Country country);

    void edit(Country country);

    void remove(Country country);

    void remove(Long id);

    Country find(Long id);

    List<Country> findAll();
}
