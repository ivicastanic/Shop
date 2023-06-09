package com.shop.UI.paneli;

import com.shop.UI.Controller;
import com.shop.employee.Employee;
import com.shop.employee.service.EmployeeServiceLocal;
import jakarta.persistence.NoResultException;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;


public class LoginPanel extends GridPane {
    private final Label usernameLabel = new Label("Korisničko ime: ");
    private final Label passwordLabel = new Label("Lozinka: ");
    private final TextField usernameTextField = new TextField();
    private final PasswordField passwordField = new PasswordField();
    private final Button loginButton = new Button("Prijava");
    private final Button cancelButton = new Button("Odustani");
    private final Label messageLabel = new Label();

    public LoginPanel() {
        setHgap(10);
        setVgap(10);
        setPadding(new Insets(25, 25, 25, 25));
        setAlignment(Pos.CENTER);

        //username
        add(usernameLabel, 0, 0);
        add(usernameTextField, 1, 0);
        //password
        add(passwordLabel, 0, 1);
        add(passwordField, 1, 1);
        //FlowPane
        FlowPane flowPane = new FlowPane();
        flowPane.setAlignment(Pos.CENTER_RIGHT);
        loginButton.setOnAction(this::onLoginButtonClick);
        cancelButton.setOnAction(this::onCancelButtonClick);
        flowPane.setHgap(5);
        flowPane.getChildren().addAll(loginButton, cancelButton);
        add(flowPane, 1, 2);
        //message
        add(messageLabel, 1, 3);
    }

    private void onCancelButtonClick(ActionEvent event) {
        usernameTextField.clear();
        passwordField.clear();
        messageLabel.setText("");
    }


    private void onLoginButtonClick(ActionEvent event) {
        String username = usernameTextField.getText();
        String password = passwordField.getText();
        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            messageLabel.setText("Nije dozvoljeno prazan unos korisničkog imena ili lozinke");
            return;
        }
        login(username, password);
    }

    private void login(String username, String password) {
        try {
            Employee employee = EmployeeServiceLocal.SERVICE.findbyUsername(username);
            if (employee != null && password.equals(employee.getPassword())) {
                Controller.setCurrentEmployee(employee);
                Scene scene = new Scene(new ShopPanel());
                Controller.instance().getMainStage().setScene(scene);
                Controller.instance().getMainStage().setTitle("Shop");
            } else {
                messageLabel.setText("Neispravna lozinka.");
            }
        } catch (NoResultException e) {
            messageLabel.setText("Nesipravno korisničko ime.");
            System.err.println(e.getMessage());
        }
    }
}
