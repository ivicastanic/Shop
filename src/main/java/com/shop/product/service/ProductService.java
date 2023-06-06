package com.shop.product.service;

import com.shop.product.Product;
import com.shop.service.AbstractService;

class ProductService extends AbstractService<Product> implements ProductServiceLocal {
    public ProductService() {
        super(Product.class);
    }

}
