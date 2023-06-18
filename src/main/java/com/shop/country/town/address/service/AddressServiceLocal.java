package com.shop.country.town.address.service;

import com.shop.country.town.Town;
import com.shop.country.town.address.Address;


import java.util.List;

public interface AddressServiceLocal {
    AddressServiceLocal SERVICE = new AddressService();

    void create(Address address);

    void edit(Address address);

    void remove(Address address);

    void remove(Long id);

    Address find(Long id);

    List<Address> findAll();

    Address findByName(String name);
}
