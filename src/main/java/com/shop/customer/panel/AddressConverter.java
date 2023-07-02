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
        StringTokenizer stringTokenizer=new StringTokenizer(s,",");
        Address address=new Address();
        address.setName(stringTokenizer.nextToken());
        Town town= TownServiceLocal.SERVICE.findByName(stringTokenizer.nextToken());
        Country country= CountryServiceLocal.SERVICE.findByName(stringTokenizer.nextToken());
        town.setCountry(country);
        address.setTown(town);
        try{
            address=AddressServiceLocal.SERVICE.findByName(address.getName());
        }catch (Exception e){
            AddressServiceLocal.SERVICE.create(address);
        }
        return address;
    }
}
