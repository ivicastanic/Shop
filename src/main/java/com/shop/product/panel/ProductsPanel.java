package com.shop.product.panel;

import com.shop.UI.Controller;
import com.shop.UI.paneli.ShopPanel;
import com.shop.customer.Customer;
import com.shop.customer.panel.AddCustomerPanel;
import com.shop.customer.service.CustomerServiceLocal;
import com.shop.product.Product;
import com.shop.product.service.ProductServiceLocal;
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
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;

import java.math.BigDecimal;
import java.util.function.Consumer;

public class ProductsPanel extends VBox {
    private Label currentEmployeeLabel = new Label();
    private final Button backButton = new Button("Back");
    private TableView<Product> productTableView = new TableView<>();
    private ObservableList<Product> productObservableList;
    private TextField searchTextField = new TextField();
    private Button searchButton = new Button("Search");
    private final Button addProductButton = new Button("Add Product");
    private final Button deleteProductButton = new Button("Delete Product");
    private final CheckBox deleteCheckBox = new CheckBox("Delete");
    private final RadioButton nameRadioButton=new RadioButton("Name search");
    private final RadioButton descriptionRadioButton=new RadioButton("Description search");
    private final TextField quantityTextField=new TextField();
    private final Button getItemButton=new Button("Get Item");
    
    public  ProductsPanel(){
        setSpacing(10);
        setPadding(new Insets(10));

        BorderPane backAndEmployeePanel = getBackAndEmployeePanel();
        setupTableView();
        HBox radioHBox=getRadioPanel();
        HBox searchHBox=getSearchPanel();
        HBox getItemHBox=getItemPanel();
        HBox buttonPanel=getButtonPanel();

        getChildren().addAll(backAndEmployeePanel,productTableView,radioHBox,searchHBox,getItemHBox,buttonPanel);
    }

    private HBox getButtonPanel() {
        HBox buttonHBox = new HBox(10);
        buttonHBox.getChildren().addAll(addProductButton/*,deleteProductButton, deleteCheckBox*/);
        addProductButton.setOnAction(this::onClickAddCustomerButton);
        deleteProductButton.setOnAction(this::onClickDeleteCustomerButton);
        deleteProductButton.setDisable(true);
        deleteCheckBox.setOnAction(this::onClickDeleteCheckBox);
        return buttonHBox;
    }

    private void onClickAddCustomerButton(ActionEvent actionEvent) {
        Controller.instance().setProductsPanel(this);
        Scene scene = new Scene(new AddProductPanel());
        Stage stage = Controller.instance().getAddProductStage();
        stage.setScene(scene);
        stage.setTitle("Add product");
        stage.show();
    }

    private void onClickDeleteCustomerButton(ActionEvent actionEvent) {
        int numberSelectedProducts=0;
        for(int i=0;i<productObservableList.size();i++){
            if(productTableView.getSelectionModel().isSelected(i)){
                numberSelectedProducts++;
            }
        }
        if (numberSelectedProducts!=1) {
            Controller.instance().showDialog("Selektujte proizvod kojeg želite izbrisati");
        } else {
            Product selectedProduct = productTableView.getSelectionModel().getSelectedItem();
            productObservableList.remove(selectedProduct);
            ProductServiceLocal.SERVICE.remove(selectedProduct.getId());
        }
    }

    private void onClickDeleteCheckBox(ActionEvent actionEvent) {
        if (deleteCheckBox.isSelected()) {
            deleteProductButton.setDisable(false);
        } else {
            deleteProductButton.setDisable(true);
        }
    }

    private HBox getItemPanel() {
        quantityTextField.setPromptText("Number of items..");
        getItemButton.setOnAction(this::onClickGetButton);
        getItemButton.setMinWidth(70);
        HBox getItemHBox=new HBox(20);
        getItemHBox.getChildren().addAll(quantityTextField,getItemButton);
        return getItemHBox;
    }

    private void onClickGetButton(ActionEvent actionEvent) {
        ObservableList<Product> selectedProducts = productTableView.getSelectionModel().getSelectedItems();
        int numberItems=0;
        for (int i=0;i<selectedProducts.size();i++){
            numberItems+=selectedProducts.get(i).getQuantity();
        }
        quantityTextField.setText(String.valueOf(numberItems));
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
        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        nameColumn.setOnEditCommit(event -> onFiledChange(event, r -> r.setName(event.getNewValue())));

        TableColumn<Product, String> descriptionColumn = new TableColumn<>("Opis");
        descriptionColumn.setMinWidth(200);
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        descriptionColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        descriptionColumn.setOnEditCommit(event -> onFiledChange(event, r -> r.setDescription(event.getNewValue())));

        TableColumn<Product, Double> priceColumn = new TableColumn<>("Jedinična cijena");
        priceColumn.setMinWidth(200);
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        priceColumn.setCellFactory(TextFieldTableCell.<Product, Double>forTableColumn(new DoubleStringConverter()));
        priceColumn.setOnEditCommit(event -> onFiledChange(event, r -> r.setPrice(event.getNewValue())));

        TableColumn<Product, Integer> quantityColumn = new TableColumn<>("Količina");
        quantityColumn.setMinWidth(200);
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        quantityColumn.setCellFactory(TextFieldTableCell.<Product, Integer>forTableColumn(new IntegerStringConverter()));
        quantityColumn.setOnEditCommit(event -> onFiledChange(event, r -> r.setQuantity(event.getNewValue())));



        productTableView.getColumns().addAll(nameColumn, descriptionColumn, priceColumn, quantityColumn);
    }

    private <E> void onFiledChange(TableColumn.CellEditEvent<Product, E> event, Consumer<Product> productConsumer) {
        Product editProduct = event.getRowValue();
        productConsumer.accept(editProduct);
        ProductServiceLocal.SERVICE.edit(editProduct);
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

    public ObservableList<Product> getProductObservableList() {
        return productObservableList;
    }
}
