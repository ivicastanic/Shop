package com.shop.employee.panel;

import com.shop.UI.Controller;
import com.shop.UI.paneli.ShopPanel;
import com.shop.employee.Employee;
import com.shop.employee.privilege.Privilege;
import com.shop.employee.service.EmployeeServiceLocal;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class EmployeePanel extends VBox {
    private Label currentEmployeeLabel = new Label();
    private final Button backButton = new Button("Back");
    private final TableView<Employee> employeeTableView = new TableView<>();
    private ObservableList<Employee> employeeObservableList;
    private final Button addEmployeeButton = new Button("Add Employee");
    private final Button deleteEmployeeButton = new Button("Delete Employee");
    private final CheckBox deleteCheckBox = new CheckBox("Delete");



    public EmployeePanel(){
        setSpacing(10);
        setPadding(new Insets(10));

        BorderPane backAndEmployeePanel = getBackAndEmployeePanel();
        setupTableView();
        HBox buttonPanel = getButtonPanel();


        getChildren().addAll(backAndEmployeePanel,employeeTableView,buttonPanel);
    }

    private HBox getButtonPanel() {
        HBox hBox = new HBox(10);
        hBox.getChildren().addAll(addEmployeeButton, deleteEmployeeButton, deleteCheckBox);
        addEmployeeButton.setOnAction(this::onClickAddEmployeeButton);
        deleteEmployeeButton.setOnAction(this::onClickDeleteEmployeeButton);
        deleteEmployeeButton.setDisable(true);
        deleteCheckBox.setOnAction(this::onClickDeleteCheckBox);
        return hBox;
    }

    private void onClickAddEmployeeButton(ActionEvent actionEvent) {
    }

    private void onClickDeleteEmployeeButton(ActionEvent actionEvent) {
    }

    private void onClickDeleteCheckBox(ActionEvent actionEvent) {
        if (deleteCheckBox.isSelected()) {
            deleteEmployeeButton.setDisable(false);
        } else {
            deleteEmployeeButton.setDisable(true);
        }
    }


    private void setupTableView() {
        employeeObservableList = EmployeeServiceLocal.SERVICE.loadEmployee();
        employeeTableView.setItems(employeeObservableList);

        TableColumn<Employee, Integer> idColumn = new TableColumn<>("Id");
        idColumn.setMinWidth(100);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Employee, String> nameColumn = new TableColumn<>("Ime");
        nameColumn.setMinWidth(200);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Employee, String> surnameColumn = new TableColumn<>("Prezime");
        surnameColumn.setMinWidth(200);
        surnameColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));

        TableColumn<Employee, String> contactColumn = new TableColumn<>("Kontakt");
        contactColumn.setMinWidth(200);
        contactColumn.setCellValueFactory(new PropertyValueFactory<>("contact"));

        TableColumn<Employee, Privilege> privilegeColumn = new TableColumn<>("Privilegija");
        privilegeColumn.setMinWidth(200);
        privilegeColumn.setCellValueFactory(new PropertyValueFactory<>("privilege"));

        employeeTableView.getColumns().addAll(idColumn, nameColumn, surnameColumn,contactColumn, privilegeColumn);
    }

    private BorderPane getBackAndEmployeePanel() {
        currentEmployeeLabel= Controller.getCurrentEmployeeLabel();
        backButton.setOnAction(this::onClickLogoutButton);
        return new BorderPane(null, null, currentEmployeeLabel, null, backButton);
    }

    private void onClickLogoutButton(ActionEvent actionEvent) {
        Scene scene = new Scene(new ShopPanel());
        Controller.instance().getMainStage().setScene(scene);
        Controller.instance().getMainStage().setTitle("Shop");
    }


}
