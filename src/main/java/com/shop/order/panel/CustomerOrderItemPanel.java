package com.shop.order.panel;

import com.shop.UI.Controller;
import com.shop.customer.Customer;
import com.shop.customer.service.CustomerServiceLocal;
import com.shop.order.Order;
import com.shop.order.order_item.OrderItem;
import com.shop.order.order_status.OrderStatus;
import com.shop.order.service.OrderServiceLocal;
import com.shop.product.Product;
import com.shop.product.service.ProductServiceLocal;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.List;

public class CustomerOrderItemPanel extends BorderPane {
    private Order currentOrder;
    private Stage currentStage;
    private final Button backButton = new Button("Back");
    private ListView<OrderItem> orderItemListView = new ListView<>();
    private ObservableList<OrderItem> orderItemObservableList;
    private final Label saldoLabel = new Label();
    private final Button finishButton = new Button("Finish");
    private final ComboBox<OrderStatus> orderStatusComboBox = new ComboBox<>();
    private final Button cancelButton = new Button("Cacnel");


    public CustomerOrderItemPanel(Order order, Stage stage) {
        setTop(backButton);
        setCenter(orderItemListView);
        setOpaqueInsets(new Insets(10));
        setPadding(new Insets(10));

        currentOrder = order;
        currentStage = stage;
        orderItemObservableList = orderItemListView.getItems();
        List<OrderItem> orderList = currentOrder.getOrderItemList();
        orderItemObservableList.addAll(orderList);
        orderItemListView.setMaxHeight(300);
        backButton.setOnAction(this::onClickBackButon);
        saldoLabel.setText(getStringSaldo());
        saldoLabel.setFont(new Font(20));


        HBox buttonPanel = getButtonPanel();
        if(currentOrder.getOrderStatus().getId()==1){
            BorderPane borderPane = new BorderPane(null,null,null,null,saldoLabel);
            setBottom(borderPane);
        }else{
            setUpStatusComboBox();
            BorderPane borderPane = new BorderPane(null,null,buttonPanel,null,saldoLabel);
            setBottom(borderPane);
        }
    }

    private HBox getButtonPanel() {
        HBox buttonPanel=new HBox(10);
        buttonPanel.getChildren().addAll(cancelButton,orderStatusComboBox, finishButton);
        cancelButton.setOnAction(this::onclickCancelButton);
        finishButton.setOnAction(this::onClickFinishButton);
        return buttonPanel;
    }

    private void onclickCancelButton(ActionEvent actionEvent) {
        Customer customer=currentOrder.getCustomer();
        customer.getOrderList().remove(currentOrder);
        OrderServiceLocal.SERVICE.remove(currentOrder);
        backButton.fire();
    }

    private void onClickFinishButton(ActionEvent actionEvent) {
        if(orderStatusComboBox.getValue().getName().equals("Prodano")){
            for(int i=0;i<currentOrder.getOrderItemList().size();i++){
                OrderItem orderItem=currentOrder.getOrderItemList().get(i);
                Product product=orderItem.getProduct();
                if(product.getQuantity()<orderItem.getQuantity()){
                    Controller.instance().showDialog("Na stanju je trenutno: "+product.getQuantity()+" "+product.getName()+", "+product.getDescription());
                    return;
                }
                product.setQuantity(product.getQuantity()-orderItem.getQuantity());
                ProductServiceLocal.SERVICE.edit(product);
            }
            currentOrder.setOrderStatus(orderStatusComboBox.getValue());
            OrderServiceLocal.SERVICE.edit(currentOrder);
            Customer customer=currentOrder.getCustomer();
            customer.setPoints(customer.getPoints()+(int) getSaldo()/20);
            CustomerServiceLocal.SERVICE.edit(customer);
            backButton.fire();
            Controller.instance().getCustomersPanel().refresh();
        }
    }


    private void setUpStatusComboBox() {
        ObservableList<OrderStatus> orderStatusObservableList = orderStatusComboBox.getItems();
        List<OrderStatus> orderStatusList = OrderServiceLocal.SERVICE.findAllOrderStatus();
        orderStatusObservableList.addAll(orderStatusList);
        orderStatusComboBox.setValue(orderStatusList.get(1));
    }

    private String getStringSaldo() {
        return "Ukupno: " + getSaldo() + "KM";
    }

    private double getSaldo(){
        double saldo = 0.;
        for (int i = 0; i < orderItemObservableList.size(); i++) {
            OrderItem orderItem = orderItemObservableList.get(i);
            saldo += orderItem.getUnitPrice() * orderItem.getQuantity();
        }
        return saldo;
    }


    private void onClickBackButon(ActionEvent actionEvent) {
        Customer selectedCustomer = currentOrder.getCustomer();
        CustomerOrderPanel customerOrderPanel = new CustomerOrderPanel(selectedCustomer, currentStage);
        Scene scene = new Scene(customerOrderPanel, 500, 400);
        currentStage.setScene(scene);
        currentStage.setTitle("Narudžbe kupca");
        currentStage.show();
    }
}
