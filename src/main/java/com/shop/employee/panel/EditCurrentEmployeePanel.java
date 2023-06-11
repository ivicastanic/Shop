package com.shop.employee.panel;

import com.shop.UI.Controller;
import com.shop.employee.Employee;
import com.shop.employee.service.EmployeeServiceLocal;
import jakarta.persistence.*;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

public class EditCurrentEmployeePanel extends GridPane {
    private final TextField nameTextField = new TextField();
    private final Label nameLabel = new Label("Ime: ");
    private final TextField surnameTextField = new TextField();
    private final Label surnameLabel = new Label("Prezime: ");
    private final TextField usernameTextField = new TextField();
    private final Label usernameLabel = new Label("Korisničko ime: ");
    private final PasswordField passwordField = new PasswordField();
    private final Label passwordLabel = new Label("Lozinka: ");
    private final TextField contactTextField = new TextField();
    private final Label contactLabel = new Label("Kontakt: ");
    private final Button saveEmployeeButton = new Button("SAVE");
    private Employee employee;

    public EditCurrentEmployeePanel(){
        setHgap(10);
        setVgap(10);
        setPadding(new Insets(20));

        employee = Controller.getCurrentEmployee();

        //forma za editovanje
        nameTextField.setMaxWidth(200);
        nameTextField.setPromptText("Enter name...");
        nameTextField.setText(employee.getName());
        surnameTextField.setMaxWidth(200);
        surnameTextField.setPromptText("Enter surname...");
        surnameTextField.setText(employee.getSurname());
        usernameTextField.setMaxWidth(200);
        usernameTextField.setPromptText("Enter username...");
        usernameTextField.setText(employee.getUsername());
        passwordField.setMaxWidth(200);
        passwordField.setPromptText("Enter password...");
        passwordField.setText(employee.getPassword());
        contactTextField.setMaxWidth(200);
        contactTextField.setPromptText("Enter contact...");
        contactTextField.setText(employee.getContact());
        add(nameLabel, 0, 0);
        add(nameTextField, 1, 0);
        add(surnameLabel, 0, 1);
        add(surnameTextField, 1, 1);
        add(usernameLabel, 0, 2);
        add(usernameTextField, 1, 2);
        add(passwordLabel, 0, 3);
        add(passwordField, 1, 3);
        add(contactLabel,0,4);
        add(contactTextField,1,4);
        add(saveEmployeeButton,0,5);
        
        saveEmployeeButton.setOnAction(this::onClickSaveEmployeeButton);
    }

    private void onClickSaveEmployeeButton(ActionEvent actionEvent) {
        if (nameTextField.getText().isEmpty() || surnameTextField.getText().isEmpty() || usernameTextField.getText().isEmpty() || passwordField.getText().isEmpty() || contactTextField.getText().isEmpty()) {
            Dialog dialog = new Dialog<>();
            dialog.setTitle("Greška");
            dialog.setContentText("Niste popunili sva polja!");
            dialog.show();
            dialog.setHeight(150);
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL);
        } else {
            try {
                Employee employee= EmployeeServiceLocal.SERVICE.findbyUsername(usernameTextField.getText());
                if(this.employee.getUsername().equals(usernameTextField.getText())){
                    throw new NoResultException();
                }
                if (employee != null && !this.employee.getUsername().equals(usernameTextField.getText())) {
                    Dialog dialog = new Dialog<>();
                    dialog.setTitle("Greška");
                    dialog.setContentText("Korisničko ime je zauzeto!");
                    dialog.show();
                    dialog.setHeight(150);
                    dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL);
                }
            } catch (NoResultException e) {
                if (passwordField.getText().length() < 6) {
                    Dialog dialog = new Dialog<>();
                    dialog.setTitle("Greška");
                    dialog.setContentText("Lozinka je prekratka (minimalno 6 karaktera)!");
                    dialog.show();
                    dialog.setHeight(150);
                    dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL);
                }else{
                    employee.setName(nameTextField.getText());
                    employee.setSurname(surnameTextField.getText());
                    employee.setUsername(usernameTextField.getText());
                    employee.setPassword(passwordField.getText());
                    employee.setContact(contactTextField.getText());
                    EmployeeServiceLocal.SERVICE.edit(employee);
                }
            }
        }
    }

}
