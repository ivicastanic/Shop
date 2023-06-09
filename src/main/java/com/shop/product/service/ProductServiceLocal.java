package com.shop.product.service;

import com.shop.customer.Customer;
import com.shop.product.Product;
import javafx.collections.ObservableList;

import java.util.List;

public interface ProductServiceLocal {

    ProductServiceLocal SERVICE=new ProductService();

    void create(Product product);

    void edit(Product product);

    void remove(Product product);
    void remove(Long id);

    Product find(Long id);

    List<Product> findAll();
    ObservableList<Product> loadProducts();

}