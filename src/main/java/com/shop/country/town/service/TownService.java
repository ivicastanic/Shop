package com.shop.country.town.service;

import com.shop.country.town.Town;
import com.shop.service.AbstractService;

class TownService extends AbstractService<Town> implements TownServiceLocal {
    public TownService() {
        super(Town.class);
    }
}
