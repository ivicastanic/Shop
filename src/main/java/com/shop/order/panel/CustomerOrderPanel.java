package com.shop.order.panel;

import com.shop.UI.Controller;
import com.shop.customer.Customer;
import com.shop.order.Order;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.List;

public class CustomerOrderPanel extends BorderPane {

    private Customer currentCustomer;
    private Stage currentStage;
    private ListView<Order> orderListView = new ListView<>();
    private ObservableList<Order> orderObservableList;
    private final Button detailsButton=new Button("Details");

    public CustomerOrderPanel(Customer customer,Stage stage){
        setCenter(orderListView);
        setBottom(detailsButton);
        setOpaqueInsets(new Insets(10));
        setPadding(new Insets(10));

        currentCustomer=customer;
        currentStage=stage;
        orderObservableList = orderListView.getItems();
        List<Order> orderList=customer.getOrderList();
        orderObservableList.addAll(orderList);
        orderListView.setMaxHeight(300);
        
        detailsButton.setOnAction(this::onClickDetailsButton);
    }

    private void onClickDetailsButton(ActionEvent actionEvent) {
        if (orderListView.getSelectionModel().getSelectedItem() == null) {
            Controller.instance().showDialog("Selektujte narudžbu da vidite detalje");
        }else {
            Order selectedOrder = orderListView.getSelectionModel().getSelectedItem();
            CustomerOrderItemPanel customerOrderItemPanel=new CustomerOrderItemPanel(selectedOrder,currentStage);
            Scene scene = new Scene(customerOrderItemPanel,500,400);
            currentStage.setScene(scene);
            currentStage.setTitle("Narudžba kupca");
        }
    }
}
