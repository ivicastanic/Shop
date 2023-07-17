package com.shop.customer.panel;

import com.shop.country.Country;
import com.shop.country.service.CountryServiceLocal;
import com.shop.country.town.Town;
import com.shop.country.town.address.Address;
import com.shop.country.town.address.service.AddressServiceLocal;
import com.shop.country.town.service.TownServiceLocal;
import javafx.util.StringConverter;

import java.util.StringTokenizer;

public class AddressConverter extends StringConverter<Address> {
    @Override
    public String toString(Address address) {
        return address.toString();
    }

    @Override
    public Address fromString(String s) {
        StringTokenizer stringTokenizer = new StringTokenizer(s, ",");
        Address address = new Address();
        address.setName(stringTokenizer.nextToken());
        Town town = new Town();
        town.setName(stringTokenizer.nextToken());
        Country country = new Country();
        country.setName(stringTokenizer.nextToken());
        try {
            country = CountryServiceLocal.SERVICE.findByName(country.getName());
        } catch (Exception e) {
            CountryServiceLocal.SERVICE.create(country);
        }
        town.setCountry(country);
        try {
            town = TownServiceLocal.SERVICE.findByName(town.getName());
        } catch (Exception e) {
            TownServiceLocal.SERVICE.create(town);
        }
        address.setTown(town);
        try {
            address = AddressServiceLocal.SERVICE.findByName(address.getName());
        } catch (Exception e) {
            AddressServiceLocal.SERVICE.create(address);
        }
        System.out.println(address);
        return address;
    }
}
