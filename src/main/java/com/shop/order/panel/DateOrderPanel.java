package com.shop.order.panel;

import com.shop.UI.Controller;
import com.shop.order.Order;
import com.shop.order.service.OrderServiceLocal;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.List;

public class DateOrderPanel extends BorderPane {
    private LocalDate currentDate;
    private Stage currentStage;
    private ListView<Order> orderListView = new ListView<>();
    private ObservableList<Order> orderObservableList;
    private final Button detailsButton=new Button("Details");

    public DateOrderPanel(LocalDate date, Stage stage){
        setCenter(orderListView);
        setBottom(detailsButton);
        setOpaqueInsets(new Insets(10));
        setPadding(new Insets(10));

        currentDate=date;
        currentStage=stage;
        orderObservableList = orderListView.getItems();
        List<Order> orderList= OrderServiceLocal.SERVICE.findByDate(currentDate);
        orderObservableList.addAll(orderList);
        orderListView.setMaxHeight(300);

        detailsButton.setOnAction(this::onClickDetailsButton);
    }

    private void onClickDetailsButton(ActionEvent actionEvent) {
        if (orderListView.getSelectionModel().getSelectedItem() == null) {
            Controller.instance().showDialog("Selektujte narudžbu da vidite detalje");
        }else {
            Order selectedOrder = orderListView.getSelectionModel().getSelectedItem();
            DateOrderItemPanel dateOrderItemPanel=new DateOrderItemPanel(selectedOrder,currentStage);
            Scene scene = new Scene(dateOrderItemPanel,500,400);
            currentStage.setScene(scene);
            currentStage.setTitle("Narudžba");
        }
    }
}
