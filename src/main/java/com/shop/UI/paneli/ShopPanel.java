package com.shop.UI.paneli;

import com.shop.UI.Controller;
import com.shop.employee.panel.EmployeePanel;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.time.LocalDate;

public class ShopPanel extends VBox {
    private Hyperlink currentEmployeeLink = new Hyperlink();
    private final Label shopLabel = new Label("Shop");
    private final Button productsButton = new Button("Proizvodi");
    private final Button customersButton = new Button("Kupci");
    private final Button employeesButton = new Button("Zaposleni");
    private final Button logoutButton = new Button("Odjava");
    private final Label dateLabel = new Label();
    private final Label saldoLabel = new Label("10000KM");

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

    }

    private void onClickEmployeesButton(ActionEvent actionEvent) {
        Scene scene=new Scene(new EmployeePanel());
        Controller.instance().getMainStage().setScene(scene);
        Controller.instance().getMainStage().setTitle("Zaposlenici");
    }

    private void onClickProductButton(ActionEvent actionEvent) {
    }

    private BorderPane getLogoutAndEmployeePanel() {
        currentEmployeeLink.setText(Controller.getCurrentEmployeeLabel().getText());
        currentEmployeeLink.setOnAction(this::onClickCurrentEmployeeLink);
        logoutButton.setOnAction(this::onClickLogoutButton);
        return new BorderPane(null, null, currentEmployeeLink, null, logoutButton);
    }

    private void onClickCurrentEmployeeLink(ActionEvent actionEvent) {
    }

    private FlowPane getButtonAndDatePanel() {
        dateLabel.setText("Datum: " + returnDate());
        VBox dateAndSaldoPanel = new VBox(dateLabel, saldoLabel);
        dateAndSaldoPanel.setSpacing(10);

        VBox buttonPanel=getButtonPanel();
        FlowPane buttonAndDatePanel= new FlowPane(buttonPanel, dateAndSaldoPanel);
        buttonAndDatePanel.setAlignment(Pos.CENTER);
        buttonAndDatePanel.setPadding(new Insets(5));
        buttonAndDatePanel.setHgap(30);
        return buttonAndDatePanel;
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

    private String returnDate() {
        LocalDate localDate = LocalDate.now();
        return localDate.toString();
    }
}