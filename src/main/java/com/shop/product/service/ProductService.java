package com.shop.product.service;

import com.shop.customer.Customer;
import com.shop.product.Product;
import com.shop.service.AbstractService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

class ProductService extends AbstractService<Product> implements ProductServiceLocal {
    public ProductService() {
        super(Product.class);
    }


    @Override
    public ObservableList<Product> loadProducts() {
        List<Product> products=findAll();
        return FXCollections.observableList(products);
    }
}
