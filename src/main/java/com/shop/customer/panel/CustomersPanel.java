package com.shop.customer.panel;

import com.shop.UI.Controller;
import com.shop.UI.paneli.ShopPanel;
import com.shop.country.town.address.Address;
import com.shop.customer.Customer;
import com.shop.customer.service.CustomerServiceLocal;
import com.shop.order.panel.CustomerOrderPanel;
import com.shop.order.panel.NewOrderpanel;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import javafx.util.converter.LocalDateStringConverter;


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
    private final Button ordersButton=new Button("Orders");
    private final Button newOrderButton=new Button("New order");


    public CustomersPanel(){
        setSpacing(10);
        setPadding(new Insets(10));

        BorderPane backAndEmployeePanel = getBackAndEmployeePanel();
        setupTableView();
        BorderPane searchPanel = getSearchPanel();
        HBox buttonPanel = getButtonPanel();

        getChildren().addAll(backAndEmployeePanel,customerTableView,searchPanel,buttonPanel);
    }

    private HBox getButtonPanel() {
        HBox hBox = new HBox(10);
        hBox.getChildren().addAll(addCustomerButton, deleteCustomerButton, deleteCheckBox);
        addCustomerButton.setOnAction(this::onClickAddCustomerButton);
        deleteCustomerButton.setOnAction(this::onClickDeleteCustomerButton);
        deleteCustomerButton.setDisable(true);
        deleteCheckBox.setOnAction(this::onClickDeleteCheckBox);
        return hBox;
    }

    private void onClickAddCustomerButton(ActionEvent actionEvent) {
        Controller.instance().setCustomersPanel(this);
        Scene scene = new Scene(new AddCustomerPanel());
        Stage stage = Controller.instance().getAddCustomerStage();
        stage.setScene(scene);
        stage.setTitle("Add customer");
        stage.show();
    }

    private void onClickDeleteCustomerButton(ActionEvent actionEvent) {
        int numberSelectedCustomer=0;
        for(int i=0;i<customerObservableList.size();i++){
            if(customerTableView.getSelectionModel().isSelected(i)){
                numberSelectedCustomer++;
            }
        }
        if (numberSelectedCustomer!=1) {
            Controller.instance().showDialog("Selektujte kupca kojeg želite izbrisati");
        } else {
            Customer selectedCustomer = customerTableView.getSelectionModel().getSelectedItem();
            customerObservableList.remove(selectedCustomer);
            CustomerServiceLocal.SERVICE.remove(selectedCustomer.getId());
        }
    }

    private void onClickDeleteCheckBox(ActionEvent actionEvent) {
        if (deleteCheckBox.isSelected()) {
            deleteCustomerButton.setDisable(false);
        } else {
            deleteCustomerButton.setDisable(true);
        }
    }

    private BorderPane getSearchPanel() {
        HBox searchPanel = new HBox(10);
        searchPanel.getChildren().addAll(searchTextField, searchButton);
        searchTextField.setPromptText("Search name or surname...");
        searchButton.setOnAction(this::onClickSearchButton);
        ordersButton.setOnAction(this::onClickOrdersButton);
        newOrderButton.setOnAction(this::onClickNewOrderButton);

        HBox orderPanel=new HBox(10);
        orderPanel.getChildren().addAll(ordersButton,newOrderButton);
        BorderPane buttonBorderPane = new BorderPane(null, null, orderPanel, null, searchPanel);
        return buttonBorderPane;
    }

    private void onClickNewOrderButton(ActionEvent actionEvent) {
        int numberSelectedCustomer=0;
        for(int i=0;i<customerObservableList.size();i++){
            if(customerTableView.getSelectionModel().isSelected(i)){
                numberSelectedCustomer++;
            }
        }
        if (numberSelectedCustomer!=1) {
            Controller.instance().showDialog("Selektujte kupca da bi izvršili novu narudžbu");
        }else{
            Customer selectedCustomer = customerTableView.getSelectionModel().getSelectedItem();
            Scene scene=new Scene(new NewOrderpanel(selectedCustomer));
            Controller.instance().getMainStage().setScene(scene);
            Controller.instance().getMainStage().setTitle("New order");
        }
    }

    private void onClickOrdersButton(ActionEvent actionEvent) {
        int numberSelectedCustomer=0;
        for(int i=0;i<customerObservableList.size();i++){
            if(customerTableView.getSelectionModel().isSelected(i)){
                numberSelectedCustomer++;
            }
        }
        if (numberSelectedCustomer!=1) {
            Controller.instance().showDialog("Selektujte kupca da bi vidili njegove narudžbe");
        } else {
            Controller.instance().setCustomersPanel(this);
            Stage stage=new Stage();
            Customer selectedCustomer = customerTableView.getSelectionModel().getSelectedItem();
            CustomerOrderPanel customerOrderPanel=new CustomerOrderPanel(selectedCustomer,stage);
            Scene scene=new Scene(customerOrderPanel,500,400);
            stage.setScene(scene);
            stage.setTitle("Narudžbe kupca");
            stage.show();
        }
    }

    private void onClickSearchButton(ActionEvent actionEvent) {
        if (searchTextField.getText().isEmpty()) {
            Controller.instance().showDialog("Unesite ime ili prezime zaposlenika kojeg želite");
        } else {
            for (int i = 0; i < customerObservableList.size(); i++) {
                customerTableView.getSelectionModel().clearSelection();
            }
            for (int i = 0; i < customerObservableList.size(); i++) {
                if (customerObservableList.get(i).getName().equalsIgnoreCase(searchTextField.getText()) || customerObservableList.get(i).getSurname().equalsIgnoreCase(searchTextField.getText())) {
                    customerTableView.getSelectionModel().select(i);
                }
            }
        }
        searchTextField.clear();
    }

    private void setupTableView() {
        customerObservableList = CustomerServiceLocal.SERVICE.loadCustomers();
        customerTableView.setItems(customerObservableList);
        customerTableView.setEditable(true);
        MultipleSelectionModel<Customer> selectionModel=customerTableView.getSelectionModel();
        selectionModel.setSelectionMode(SelectionMode.MULTIPLE);

        TableColumn<Customer, Integer> idColumn = new TableColumn<>("Id");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Customer, String> nameColumn = new TableColumn<>("Ime");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        nameColumn.setOnEditCommit(event -> onFiledChange(event, r -> r.setName(event.getNewValue())));

        TableColumn<Customer, String> surnameColumn = new TableColumn<>("Prezime");
        surnameColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));
        surnameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        surnameColumn.setOnEditCommit(event -> onFiledChange(event, r -> r.setSurname(event.getNewValue())));

        TableColumn<Customer, LocalDate> birthdayColumn = new TableColumn<>("Datum rođenja");
        birthdayColumn.setCellValueFactory(new PropertyValueFactory<>("birthday"));
        birthdayColumn.setMinWidth(100);
        birthdayColumn.setCellFactory(TextFieldTableCell.<Customer,LocalDate>forTableColumn(new LocalDateStringConverter()));
        birthdayColumn.setOnEditCommit(event -> onFiledChange(event, r -> r.setBirthday(event.getNewValue())));

        TableColumn<Customer, Address> addressColumn = new TableColumn<>("Adresa");
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        addressColumn.setCellFactory(TextFieldTableCell.<Customer,Address>forTableColumn(new AddressConverter()));
        addressColumn.setOnEditCommit(event -> onFiledChange(event, r -> r.setAddress(event.getNewValue())));

        TableColumn<Customer, String> mobileColumn = new TableColumn<>("Mobitel");
        mobileColumn.setCellValueFactory(new PropertyValueFactory<>("mobile"));
        mobileColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        mobileColumn.setOnEditCommit(event -> onFiledChange(event, r -> r.setMobile(event.getNewValue())));

        TableColumn<Customer, String> emailColumn = new TableColumn<>("Email");
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        emailColumn.setMinWidth(150);
        emailColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        emailColumn.setOnEditCommit(event -> onFiledChange(event, r -> r.setEmail(event.getNewValue())));

        TableColumn<Customer, Integer> pointsColumn = new TableColumn<>("Poeni");
        pointsColumn.setCellValueFactory(new PropertyValueFactory<>("points"));
        pointsColumn.setMinWidth(100);
        //pointsColumn.setCellFactory(TextFieldTableCell.<Customer, Integer>forTableColumn(new IntegerStringConverter()));
        //pointsColumn.setOnEditCommit(event -> onFiledChange(event, r -> r.setPoints(event.getNewValue())));

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

    public ObservableList<Customer> getCustomerObservableList() {
        return customerObservableList;
    }

    public void refresh(){
        customerTableView.refresh();
    }

}
