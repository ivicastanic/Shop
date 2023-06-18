package com.shop.order.panel;

import com.shop.UI.Controller;
import com.shop.customer.Customer;
import com.shop.customer.panel.CustomersPanel;
import com.shop.customer.service.CustomerServiceLocal;
import com.shop.order.Order;
import com.shop.order.order_item.OrderItem;
import com.shop.order.order_item.service.OrderItemServiceLocal;
import com.shop.order.order_status.OrderStatus;
import com.shop.order.service.OrderServiceLocal;
import com.shop.product.Product;
import com.shop.product.service.ProductServiceLocal;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class NewOrderpanel extends VBox {
    private Label currentEmployeeLabel = new Label();
    private final Button backButton = new Button("Back");
    private TableView<Product> productTableView = new TableView<>();
    private ObservableList<Product> productObservableList;
    private TextField searchTextField = new TextField();
    private Button searchButton = new Button("Search");
    private final RadioButton nameRadioButton=new RadioButton("Name search");
    private final RadioButton descriptionRadioButton=new RadioButton("Description search");
    private final Spinner<Integer> spinner=new Spinner<>(0,100,1);
    private final Button addButton=new Button("Add");
    private Customer currentCustomer;
    private  Order order=new Order();
    private final ComboBox<OrderStatus> orderStatusComboBox=new ComboBox<>();
    private final Button finishButton=new Button("Finish");
    private  List<OrderItem> orderItemList=new ArrayList<>();



    public NewOrderpanel(Customer customer){
        setSpacing(10);
        setPadding(new Insets(10));

        currentCustomer=customer;
        order.setCustomer(currentCustomer);
        order.setOrderDate(LocalDate.now());
        order.setOrderStatus(orderStatusComboBox.getValue());

        BorderPane backAndEmployeePanel = getBackAndEmployeePanel();
        setupTableView();
        HBox radioHBox=getRadioPanel();
        HBox searchHBox=getSearchPanel();
        HBox buttonPanel=getButtonPanel();

        ObservableList<OrderStatus> orderStatusObservableList = orderStatusComboBox.getItems();

        EntityManagerFactory entityManagerFactory= Persistence.createEntityManagerFactory(Controller.PU_NAME);
        EntityManager entityManager=entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Query query=entityManager.createQuery("SELECT o FROM OrderStatus o");
        List<OrderStatus> orderStatusList = query.getResultList();
        entityManager.getTransaction().commit();
        orderStatusObservableList.addAll(orderStatusList);
        orderStatusComboBox.setValue(orderStatusList.get(0));

        getChildren().addAll(backAndEmployeePanel,productTableView,radioHBox,searchHBox,orderStatusComboBox,buttonPanel);
    }


    private HBox getButtonPanel() {
        HBox buttonHBox = new HBox(10);
        buttonHBox.getChildren().addAll(spinner, addButton,finishButton);
        spinner.setEditable(true);
        spinner.setMaxWidth(100);
        addButton.setOnAction(this::onClickAddButton);
        finishButton.setOnAction(this::onClickFinishButton);
        return buttonHBox;
    }

    private void onClickFinishButton(ActionEvent actionEvent) {
        if (orderItemList.isEmpty()){
            Controller.instance().showDialog("Dodajte satvke, da bi naručili");
            return;
        }
        order.setOrderStatus(orderStatusComboBox.getValue());
        OrderServiceLocal.SERVICE.create(order);
        for(int i=0;i<orderItemList.size();i++){
            OrderItem orderItem=orderItemList.get(i);
            if(order.getOrderStatus().getId()==1){
                Product product=orderItem.getProduct();
                product.setQuantity(product.getQuantity()-orderItem.getQuantity());
                ProductServiceLocal.SERVICE.edit(product);
                Customer customer=order.getCustomer();
                customer.setPoints(customer.getPoints()+(int) getSaldo()/20);
                CustomerServiceLocal.SERVICE.edit(customer);
            }
            orderItem.setOrder(order);
            OrderItemServiceLocal.SERVICE.create(orderItem);
            productTableView.refresh();
        }
        order=new Order();
        orderItemList=new ArrayList<>();
        order.setCustomer(currentCustomer);
        order.setOrderDate(LocalDate.now());
        order.setOrderStatus(orderStatusComboBox.getValue());
    }

    private double getSaldo() {
        double saldo = 0.;
        for (int i = 0; i < orderItemList.size(); i++) {
            OrderItem orderItem = orderItemList.get(i);
            saldo += orderItem.getUnitPrice() * orderItem.getQuantity();
        }
        return saldo;
    }

    private void onClickAddButton(ActionEvent actionEvent) {
        int numberSelectedProduct=0;
        for(int i=0;i<productObservableList.size();i++){
            if(productTableView.getSelectionModel().isSelected(i)){
                numberSelectedProduct++;
            }
        }
        if (numberSelectedProduct!=1) {
            Controller.instance().showDialog("Selektujte proizvod koji želite da naručite");
        }else{
            Product product=productTableView.getSelectionModel().getSelectedItem();
            if(product.getQuantity()<spinner.getValue()){
                Controller.instance().showDialog("Na stanju je trenutno: "+product.getQuantity()+" "+product.getName()+", "+product.getDescription());
                return;
            }
            OrderItem orderItem=new OrderItem();
            orderItem.setProduct(product);
            orderItem.setQuantity(spinner.getValue());
            orderItem.setUnitPrice(product.getPrice());
            orderItemList.add(orderItem);
        }
    }

    private HBox getSearchPanel() {
        searchTextField.setPromptText("Search name..");
        searchButton.setOnAction(this::onClickSearchButton);
        searchButton.setMinWidth(70);
        HBox searchHBox = new HBox(20);
        searchHBox.getChildren().addAll(searchTextField, searchButton);
        return searchHBox;
    }

    private void onClickSearchButton(ActionEvent actionEvent) {
        ObservableList<Product> list = productTableView.getItems();
        for (int i = 0; i < list.size(); i++) {
            productTableView.getSelectionModel().clearSelection();
        }
        for (int i = 0; i < list.size(); i++) {
            if (nameRadioButton.isSelected()) {
                if (list.get(i).getName().equalsIgnoreCase(searchTextField.getText())) {
                    productTableView.getSelectionModel().select(i);
                }
            }else if(descriptionRadioButton.isSelected()){
                if (list.get(i).getDescription().equalsIgnoreCase(searchTextField.getText())) {
                    productTableView.getSelectionModel().select(i);
                }
            }
        }
        searchTextField.clear();
    }

    private HBox getRadioPanel() {
        nameRadioButton.setSelected(true);
        nameRadioButton.setOnAction(this::onClickSearchRadioButton);
        descriptionRadioButton.setOnAction(this::onClickSearchRadioButton);
        ToggleGroup searchGroup = new ToggleGroup();
        nameRadioButton.setToggleGroup(searchGroup);
        descriptionRadioButton.setToggleGroup(searchGroup);
        HBox searchRadioBox = new HBox(10);
        searchRadioBox.getChildren().addAll(nameRadioButton, descriptionRadioButton);
        return searchRadioBox;
    }

    private void onClickSearchRadioButton(ActionEvent actionEvent) {
        if (actionEvent.getSource() == nameRadioButton) {
            searchTextField.setPromptText("Search name..");
        } else if (actionEvent.getSource() == descriptionRadioButton) {
            searchTextField.setPromptText("Search description..");
        }
    }

    private void setupTableView() {
        productObservableList = ProductServiceLocal.SERVICE.loadProducts();
        productTableView.setItems(productObservableList);
        productTableView.setEditable(true);
        MultipleSelectionModel<Product> selectionModel=productTableView.getSelectionModel();
        selectionModel.setSelectionMode(SelectionMode.MULTIPLE);

        TableColumn<Product, String> nameColumn = new TableColumn<>("Ime");
        nameColumn.setMinWidth(200);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Product, String> descriptionColumn = new TableColumn<>("Opis");
        descriptionColumn.setMinWidth(200);
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

        TableColumn<Product, Double> priceColumn = new TableColumn<>("Jedinična cijena");
        priceColumn.setMinWidth(200);
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableColumn<Product, Integer> quantityColumn = new TableColumn<>("Količina");
        quantityColumn.setMinWidth(200);
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        productTableView.getColumns().addAll(nameColumn, descriptionColumn, priceColumn, quantityColumn);
    }

    private BorderPane getBackAndEmployeePanel() {
        currentEmployeeLabel = Controller.getCurrentEmployeeLabel();
        backButton.setOnAction(this::onClickLogoutButton);
        return new BorderPane(null, null, currentEmployeeLabel, null, backButton);
    }

    private void onClickLogoutButton(ActionEvent actionEvent) {
        Scene scene = new Scene(new CustomersPanel());
        Controller.instance().getMainStage().setScene(scene);
        Controller.instance().getMainStage().setTitle("Customers");
    }
}
