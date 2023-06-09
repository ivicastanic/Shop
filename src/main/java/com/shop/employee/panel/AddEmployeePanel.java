package com.shop.employee.panel;

import com.shop.employee.Employee;
import com.shop.employee.service.EmployeeServiceLocal;
import jakarta.persistence.*;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class AddEmployeePanel extends GridPane {
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
    private final RadioButton adminRadioButton = new RadioButton("Admin");
    private final RadioButton userRadioButton = new RadioButton("User");
    private final Button addEmployeeButton = new Button("SAVE");

    public AddEmployeePanel(){
        setHgap(10);
        setVgap(10);
        setPadding(new Insets(20));

        //forma za editovanje
        nameTextField.setMaxWidth(200);
        nameTextField.setPromptText("Enter name...");
        surnameTextField.setMaxWidth(200);
        surnameTextField.setPromptText("Enter surname...");
        usernameTextField.setMaxWidth(200);
        usernameTextField.setPromptText("Enter username...");
        passwordField.setMaxWidth(200);
        passwordField.setPromptText("Enter password...");
        contactTextField.setMaxWidth(200);
        contactTextField.setPromptText("Enter contact...");
        add(nameLabel, 0, 0);
        add(nameTextField, 1, 0);
        add(surnameLabel, 0, 1);
        add(surnameTextField, 1, 1);
        add(usernameLabel, 0, 2);
        add(usernameTextField, 1, 2);
        add(passwordLabel, 0, 3);
        add(passwordField, 1, 3);
        add(contactLabel, 0,4);
        add(contactTextField,1,4);

        ToggleGroup toggleGroup = new ToggleGroup();
        adminRadioButton.setToggleGroup(toggleGroup);
        userRadioButton.setToggleGroup(toggleGroup);
        userRadioButton.setSelected(true);
        HBox radioButtonPanel = new HBox(10);
        radioButtonPanel.getChildren().addAll(adminRadioButton, userRadioButton);
        add(radioButtonPanel, 0, 5);

        add(addEmployeeButton, 0, 6);
        addEmployeeButton.setOnAction(this::onClickAddEmployeeButton);
    }

    private void onClickAddEmployeeButton(ActionEvent actionEvent) {
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
                if (employee != null) {
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
                    Employee employee = new Employee();
                    employee.setName(nameTextField.getText());
                    employee.setSurname(surnameTextField.getText());
                    employee.setUsername(usernameTextField.getText());
                    employee.setPassword(passwordField.getText());
                    if (adminRadioButton.isSelected()) {
                        Privilege privilege = entityManager.find(Privilege.class, 1);
                        employee.setPrivilege(privilege);
                    } else {
                        Privilege privilege = entityManager.find(Privilege.class, 2);
                        employee.setPrivilege(privilege);
                    }
                    entityManager.getTransaction().begin();
                    entityManager.persist(employee);
                    entityManager.getTransaction().commit();
                    employeeObservableList.add(employee);
                }
            }
        }
    }
}
