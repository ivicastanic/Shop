package com.shop.country.town.address.service;

import com.shop.country.town.address.Address;
import com.shop.service.AbstractService;

class AddressService extends AbstractService<Address> implements AddressServiceLocal {
    public AddressService() {
        super(Address.class);
    }
}
