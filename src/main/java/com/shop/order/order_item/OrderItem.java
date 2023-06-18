package com.shop.order.order_item;


import com.shop.order.Order;
import com.shop.product.Product;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "order_items",catalog = "shop")
public class OrderItem implements Serializable {

    @Id
    @JoinColumn(name = "id_order", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Order order;

    @Id
    @JoinColumn(name = "id_product", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Product product;
    @Basic(optional = false)
    private int quantity;
    @Basic(optional = false)
    @Column(name = "unit_price")
    private double unitPrice;

    public OrderItem() {
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderItem orderItem = (OrderItem) o;
        return Objects.equals(order, orderItem.order);
    }

    @Override
    public int hashCode() {
        return Objects.hash(order);
    }

    @Override
    public String toString() {
        return "Proizvod: "+product.getName()+"-"+product.getDescription()+",     Jedinična cijena: "+unitPrice+",     Količina: "+quantity+",     Ukupna cijena: "+quantity*unitPrice;
    }
}
