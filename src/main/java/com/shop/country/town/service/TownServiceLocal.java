package com.shop.country.town.service;


import com.shop.country.town.Town;

import java.util.List;

public interface TownServiceLocal {
    TownServiceLocal SERVICE = new TownService();

    void create(Town town);

    void edit(Town town);

    void remove(Town town);

    void remove(Long id);

    Town find(Long id);

    List<Town> findAll();
}
