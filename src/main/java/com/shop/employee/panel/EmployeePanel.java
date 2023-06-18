package com.shop.employee.panel;

import com.shop.UI.Controller;
import com.shop.UI.paneli.ShopPanel;
import com.shop.employee.Employee;
import com.shop.employee.privilege.Privilege;
import com.shop.employee.privilege.service.PrivilegeServiceLocal;
import com.shop.employee.service.EmployeeServiceLocal;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.function.Consumer;

public class EmployeePanel extends VBox {
    private Label currentEmployeeLabel = new Label();
    private final Button backButton = new Button("Back");
    private final TableView<Employee> employeeTableView = new TableView<>();
    private ObservableList<Employee> employeeObservableList;
    private TextField searchTextField = new TextField();
    private Button searchButton = new Button("SEARCH");
    private final Button addEmployeeButton = new Button("Add Employee");
    private final Button deleteEmployeeButton = new Button("Delete Employee");
    private final CheckBox deleteCheckBox = new CheckBox("Delete");


    public EmployeePanel() {
        setSpacing(10);
        setPadding(new Insets(10));


        BorderPane backAndEmployeePanel = getBackAndEmployeePanel();
        setupTableView();
        HBox searchPanel = getSearchPanel();
        HBox buttonPanel = getButtonPanel();

        getChildren().addAll(backAndEmployeePanel, employeeTableView, searchPanel, buttonPanel);
    }

    private HBox getSearchPanel() {
        HBox searchPanel = new HBox(10);
        searchPanel.getChildren().addAll(searchTextField, searchButton);
        searchTextField.setPromptText("Search name or surname...");
        searchButton.setOnAction(this::onClickSearchButton);
        return searchPanel;
    }

    private void onClickSearchButton(ActionEvent actionEvent) {
        if (searchTextField.getText().isEmpty()) {
            Controller.instance().showDialog("Unesite ime ili prezime zaposlenika kojeg želite");
        } else {
            for (int i = 0; i < employeeObservableList.size(); i++) {
                employeeTableView.getSelectionModel().clearSelection();
            }
            for (int i = 0; i < employeeObservableList.size(); i++) {
                if (employeeObservableList.get(i).getName().equalsIgnoreCase(searchTextField.getText()) || employeeObservableList.get(i).getSurname().equalsIgnoreCase(searchTextField.getText())) {
                    employeeTableView.getSelectionModel().select(i);
                }
            }
        }
        searchTextField.clear();
    }

    public ObservableList<Employee> getEmployeeObservableList() {
        return employeeObservableList;
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
        Controller.instance().setEmployeePanel(this);
        Scene scene = new Scene(new AddEmployeePanel());
        Stage stage = Controller.instance().getAddEmployeeStage();
        stage.setScene(scene);
        stage.setTitle("Add employee");
        stage.show();
    }

    private void onClickDeleteEmployeeButton(ActionEvent actionEvent) {
        int numberSelectedEmployee=0;
        for(int i=0;i<employeeObservableList.size();i++){
            if(employeeTableView.getSelectionModel().isSelected(i)){
                numberSelectedEmployee++;
            }
        }
        if (numberSelectedEmployee!=1) {
            Controller.instance().showDialog("Selektujte zaposlenika kojeg želite izbrisati");
        } else {
            Employee selectedEmployee = employeeTableView.getSelectionModel().getSelectedItem();
            employeeObservableList.remove(selectedEmployee);
            EmployeeServiceLocal.SERVICE.remove(selectedEmployee.getId());
        }
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
        employeeTableView.setEditable(true);
        MultipleSelectionModel<Employee> selectionModel=employeeTableView.getSelectionModel();
        selectionModel.setSelectionMode(SelectionMode.MULTIPLE);

        TableColumn<Employee, Integer> idColumn = new TableColumn<>("Id");
        idColumn.setMinWidth(100);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Employee, String> nameColumn = new TableColumn<>("Ime");
        nameColumn.setMinWidth(200);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        nameColumn.setOnEditCommit(event -> onFiledChange(event, r -> r.setName(event.getNewValue())));

        TableColumn<Employee, String> surnameColumn = new TableColumn<>("Prezime");
        surnameColumn.setMinWidth(200);
        surnameColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));
        surnameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        surnameColumn.setOnEditCommit(event -> onFiledChange(event, r -> r.setSurname(event.getNewValue())));

        TableColumn<Employee, String> contactColumn = new TableColumn<>("Kontakt");
        contactColumn.setMinWidth(200);
        contactColumn.setCellValueFactory(new PropertyValueFactory<>("contact"));
        contactColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        contactColumn.setOnEditCommit(event -> onFiledChange(event, r -> r.setContact(event.getNewValue())));

        TableColumn<Employee, Privilege> privilegeColumn = new TableColumn<>("Privilegija");
        privilegeColumn.setMinWidth(200);
        privilegeColumn.setCellValueFactory(new PropertyValueFactory<>("privilege"));
        Privilege[] privilegeArray = PrivilegeServiceLocal.SERVICE.findAll().toArray(new Privilege[0]);
        privilegeColumn.setCellFactory(ComboBoxTableCell.forTableColumn(privilegeArray));
        privilegeColumn.setOnEditCommit(event -> onFiledChange(event, r -> r.setPrivilege(event.getNewValue())));

        employeeTableView.getColumns().addAll(idColumn, nameColumn, surnameColumn, contactColumn, privilegeColumn);
    }

    private <E> void onFiledChange
            (TableColumn.CellEditEvent<Employee, E> event, Consumer<Employee> employeeConsumer) {
        Employee editEmployee = event.getRowValue();
        employeeConsumer.accept(editEmployee);
        EmployeeServiceLocal.SERVICE.edit(editEmployee);
    }

    private BorderPane getBackAndEmployeePanel() {
        currentEmployeeLabel = Controller.getCurrentEmployeeLabel();
        backButton.setOnAction(this::onClickLogoutButton);
        return new BorderPane(null, null, currentEmployeeLabel, null, backButton);
    }

    private void onClickLogoutButton(ActionEvent actionEvent) {
        Scene scene = new Scene(new ShopPanel());
        Controller.instance().getMainStage().setScene(scene);
        Controller.instance().getMainStage().setTitle("Shop");
    }

}
