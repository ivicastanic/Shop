package com.shop.order.panel;

import com.shop.UI.Controller;
import com.shop.customer.Customer;
import com.shop.customer.service.CustomerServiceLocal;
import com.shop.order.order_item.OrderItem;
import com.shop.order.order_item.service.OrderItemServiceLocal;
import com.shop.product.Product;
import com.shop.product.service.ProductServiceLocal;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.VBox;
import javafx.util.converter.IntegerStringConverter;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class EditNewOrderPanel extends VBox {

    private TableView<OrderItem> orderItemTableView = new TableView<>();
    private ObservableList<OrderItem> orderItemObservableList;
    private final Button deleteButton=new Button("Delete");
    private List<OrderItem>orderItemList;
    private NewOrderpanel newOrderPanel;

    public EditNewOrderPanel(NewOrderpanel newOrderpanel){
        this.newOrderPanel=newOrderpanel;
        orderItemList=newOrderpanel.getOrderItemList();
        setupTableView(orderItemList);
        deleteButton.setOnAction(this::onCLickDeleteButton);

        setSpacing(10);
        setPadding(new Insets(10));
        getChildren().addAll(orderItemTableView,deleteButton);
    }

    private void onCLickDeleteButton(ActionEvent actionEvent) {
        int numberSelectedProducts=0;
        for(int i=0;i<orderItemObservableList.size();i++){
            if(orderItemTableView.getSelectionModel().isSelected(i)){
                numberSelectedProducts++;
            }
        }
        if (numberSelectedProducts!=1) {
            Controller.instance().showDialog("Selektujte stavku koju želite izbrisati");
        } else {
            OrderItem selectedOrderItem = orderItemTableView.getSelectionModel().getSelectedItem();
            orderItemObservableList.remove(selectedOrderItem);
            newOrderPanel.refresh();
        }
    }

    private void setupTableView(List<OrderItem> orderItemList) {
        orderItemObservableList= FXCollections.observableList(orderItemList);
        orderItemTableView.setItems(orderItemObservableList);
        orderItemTableView.setEditable(true);

        TableColumn<OrderItem, Product> productColumn = new TableColumn<>("Product");
        productColumn.setMinWidth(200);
        productColumn.setCellValueFactory(new PropertyValueFactory<>("product"));

        TableColumn<OrderItem, Integer> quantityColumn = new TableColumn<>("Količina");
        quantityColumn.setMinWidth(200);
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        quantityColumn.setCellFactory(TextFieldTableCell.<OrderItem, Integer>forTableColumn(new IntegerStringConverter()));
        quantityColumn.setOnEditCommit(event -> onFiledChange(event, r -> r.setQuantity(event.getNewValue())));

        TableColumn<OrderItem, Double> priceColumn = new TableColumn<>("Cijena");
        priceColumn.setMinWidth(200);
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));

        orderItemTableView.getColumns().addAll( productColumn,quantityColumn, priceColumn);
    }

    private <E> void onFiledChange(TableColumn.CellEditEvent<OrderItem, E> event, Consumer<OrderItem> orderItemConsumer) {
        OrderItem editOrderItem = event.getRowValue();
        orderItemConsumer.accept(editOrderItem);
        orderItemList=orderItemObservableList;
        newOrderPanel.refresh();
    }
}
