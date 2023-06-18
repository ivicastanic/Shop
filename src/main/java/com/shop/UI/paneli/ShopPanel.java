package com.shop.UI.paneli;

import com.shop.UI.Controller;
import com.shop.customer.panel.CustomersPanel;
import com.shop.employee.panel.EditCurrentEmployeePanel;
import com.shop.employee.panel.EmployeePanel;
import com.shop.order.Order;
import com.shop.order.order_item.OrderItem;
import com.shop.order.panel.DateOrderPanel;
import com.shop.order.service.OrderServiceLocal;
import com.shop.product.panel.ProductsPanel;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.time.LocalDate;
import java.util.List;

public class ShopPanel extends VBox {
    private Hyperlink currentEmployeeLink = new Hyperlink();
    private final Label shopLabel = new Label("Shop");
    private final Button productsButton = new Button("Proizvodi");
    private final Button customersButton = new Button("Kupci");
    private final Button employeesButton = new Button("Zaposleni");
    private final Button logoutButton = new Button("Odjava");
    private final Label dateLabel = new Label();
    private final DatePicker datePicker = new DatePicker(LocalDate.now());
    private final Hyperlink saldoHyperlink = new Hyperlink();

    private Double saldo;

    public ShopPanel() {
        setSpacing(10);
        setPadding(new Insets(10));

        BorderPane logoutAndEmployeePanel = getLogoutAndEmployeePanel();
        BorderPane shopPanel = getShopPanel();
        FlowPane buttonAndDatePanel = getButtonAndDatePanel();
        buttonSetOnAction();

        getChildren().addAll(logoutAndEmployeePanel, shopPanel, buttonAndDatePanel);
    }

    private void buttonSetOnAction() {
        productsButton.setOnAction(this::onClickProductButton);
        employeesButton.setOnAction(this::onClickEmployeesButton);
        customersButton.setOnAction(this::onClickCustomersButton);
    }

    private void onClickCustomersButton(ActionEvent actionEvent) {
        Scene scene = new Scene(new CustomersPanel());
        Controller.instance().getMainStage().setScene(scene);
        Controller.instance().getMainStage().setTitle("Customers");
    }

    private void onClickEmployeesButton(ActionEvent actionEvent) {
        Scene scene = new Scene(new EmployeePanel());
        Controller.instance().getMainStage().setScene(scene);
        Controller.instance().getMainStage().setTitle("Employees");
    }

    private void onClickProductButton(ActionEvent actionEvent) {
        Scene scene = new Scene(new ProductsPanel());
        Controller.instance().getMainStage().setScene(scene);
        Controller.instance().getMainStage().setTitle("Products");
    }

    private BorderPane getLogoutAndEmployeePanel() {
        currentEmployeeLink.setText(Controller.getCurrentEmployeeLabel().getText());
        currentEmployeeLink.setOnAction(this::onClickCurrentEmployeeLink);
        logoutButton.setOnAction(this::onClickLogoutButton);
        return new BorderPane(null, null, currentEmployeeLink, null, logoutButton);
    }

    private void onClickCurrentEmployeeLink(ActionEvent actionEvent) {
        Scene scene = new Scene(new EditCurrentEmployeePanel());
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("My data");
        stage.show();
    }

    private FlowPane getButtonAndDatePanel() {
        dateLabel.setText("Datum: ");
        VBox dateAndSaldoPanel = new VBox(dateLabel, datePicker, saldoHyperlink);
        saldo = getSaldo();
        saldoHyperlink.setText(saldo + " KM");
        saldoHyperlink.setOnAction(this::onClickSaldoHyperLink);
        datePicker.setOnAction(this::onClickDatePicker);
        datePicker.setMaxWidth(120);
        dateAndSaldoPanel.setSpacing(10);

        VBox buttonPanel = getButtonPanel();
        FlowPane buttonAndDatePanel = new FlowPane(buttonPanel, dateAndSaldoPanel);
        buttonAndDatePanel.setAlignment(Pos.CENTER);
        buttonAndDatePanel.setPadding(new Insets(5));
        buttonAndDatePanel.setHgap(30);
        return buttonAndDatePanel;
    }

    private void onClickSaldoHyperLink(ActionEvent actionEvent) {
        Stage stage = new Stage();
        LocalDate date = datePicker.getValue();
        DateOrderPanel dateOrderPanel = new DateOrderPanel(date, stage);
        Scene scene = new Scene(dateOrderPanel, 500, 400);
        stage.setScene(scene);
        stage.setTitle("Narudžbe ");
        stage.show();

    }

    private Double getSaldo() {
        Double saldo = 0.;
        List<Order> orderList = OrderServiceLocal.SERVICE.findByDate(datePicker.getValue());
        for (int i = 0; i < orderList.size(); i++) {
            if(orderList.get(i).getOrderStatus().getId()==2){
                continue;
            }
            List<OrderItem> orderItemList = orderList.get(i).getOrderItemList();
            for (int j = 0; j < orderItemList.size(); j++) {
                OrderItem orderItem = orderItemList.get(j);
                saldo += orderItem.getUnitPrice() * orderItem.getQuantity();
            }
        }
        return saldo;
    }


    private void onClickDatePicker(ActionEvent actionEvent) {
        System.out.println("usaoooo");
        saldo = getSaldo();
        saldoHyperlink.setText(saldo + " KM");
    }

    private VBox getButtonPanel() {
        if (Controller.getCurrentEmployee().getPrivilege().getName().equals("user")) {
            employeesButton.setDisable(true);
        }
        productsButton.setMinWidth(120);
        customersButton.setMinWidth(120);
        employeesButton.setMinWidth(120);
        VBox buttonPanel = new VBox(productsButton, customersButton, employeesButton);
        buttonPanel.setSpacing(10);
        return buttonPanel;
    }

    private BorderPane getShopPanel() {
        BorderPane shopPanel = new BorderPane(shopLabel);
        shopPanel.setPadding(new Insets(20));
        shopLabel.setFont(new Font("Arial", 35));
        return shopPanel;
    }


    private void onClickLogoutButton(ActionEvent actionEvent) {
        LoginPanel loginPanel = new LoginPanel();
        Scene scene = new Scene(loginPanel);
        Controller.instance().getMainStage().setScene(scene);
        Controller.instance().getMainStage().setTitle("Login");
        Controller.setCurrentEmployee(null);
    }

}
