package com.shop.customer.panel;

import com.shop.UI.Controller;
import com.shop.UI.paneli.ShopPanel;
import com.shop.customer.Customer;
import com.shop.customer.service.CustomerServiceLocal;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;


import java.time.LocalDate;
import java.util.function.Consumer;

public class CustomersPanel extends VBox {
    private Label currentEmployeeLabel = new Label();
    private final Button backButton = new Button("Back");
    private TableView<Customer> customerTableView = new TableView<>();
    private ObservableList<Customer> customerObservableList;
    private TextField searchTextField = new TextField();
    private Button searchButton = new Button("SEARCH");
    private final Button addCustomerButton = new Button("Add Customer");
    private final Button deleteCustomerButton = new Button("Delete Customer");
    private final CheckBox deleteCheckBox = new CheckBox("Delete");


    public CustomersPanel(){
        setSpacing(10);
        setPadding(new Insets(10));

        BorderPane backAndEmployeePanel = getBackAndEmployeePanel();
        setupTableView();

        getChildren().addAll(backAndEmployeePanel,customerTableView);
    }

    private void setupTableView() {
        customerObservableList = CustomerServiceLocal.SERVICE.loadCustomers();
        customerTableView.setItems(customerObservableList);
        customerTableView.setEditable(true);
        MultipleSelectionModel<Customer> selectionModel=customerTableView.getSelectionModel();
        selectionModel.setSelectionMode(SelectionMode.MULTIPLE);

        TableColumn<Customer, Integer> idColumn = new TableColumn<>("Id");
        idColumn.setMinWidth(100);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Customer, String> nameColumn = new TableColumn<>("Ime");
        nameColumn.setMinWidth(200);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        nameColumn.setOnEditCommit(event -> onFiledChange(event, r -> r.setName(event.getNewValue())));

        TableColumn<Customer, String> surnameColumn = new TableColumn<>("Prezime");
        surnameColumn.setMinWidth(200);
        surnameColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));
        surnameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        surnameColumn.setOnEditCommit(event -> onFiledChange(event, r -> r.setSurname(event.getNewValue())));

        TableColumn<Customer, LocalDate> birthdayColumn = new TableColumn<>("Datum rođenja");
        birthdayColumn.setMinWidth(200);
        birthdayColumn.setCellValueFactory(new PropertyValueFactory<>("birthday"));

        TableColumn<Customer, String> addressColumn = new TableColumn<>("Adresa");
        addressColumn.setMinWidth(200);
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));

        TableColumn<Customer, String> mobileColumn = new TableColumn<>("Mobitel");
        mobileColumn.setMinWidth(200);
        mobileColumn.setCellValueFactory(new PropertyValueFactory<>("mobile"));
        mobileColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        mobileColumn.setOnEditCommit(event -> onFiledChange(event, r -> r.setMobile(event.getNewValue())));

        TableColumn<Customer, String> emailColumn = new TableColumn<>("Email");
        emailColumn.setMinWidth(200);
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        emailColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        emailColumn.setOnEditCommit(event -> onFiledChange(event, r -> r.setEmail(event.getNewValue())));

        TableColumn<Customer, String> pointsColumn = new TableColumn<>("Poeni");
        pointsColumn.setMinWidth(200);
        pointsColumn.setCellValueFactory(new PropertyValueFactory<>("points"));
        //pointsColumn.setCellFactory(TextFieldTableCell.<String,Integer>forTableColumn(new IntegerStringConverter()));
        //pointsColumn.setOnEditCommit(event -> onFiledChange(event, r -> r.setPoints(Integer.parseInt(event.getNewValue()))));

        customerTableView.getColumns().addAll(idColumn, nameColumn, surnameColumn, birthdayColumn, addressColumn, mobileColumn, emailColumn, pointsColumn);
    }



    private <E> void onFiledChange(TableColumn.CellEditEvent<Customer, E> event, Consumer<Customer> customerConsumer) {
        Customer editCustomer = event.getRowValue();
        customerConsumer.accept(editCustomer);
        CustomerServiceLocal.SERVICE.edit(editCustomer);
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
