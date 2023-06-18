package com.shop.order.panel;

import com.shop.order.Order;
import com.shop.order.order_item.OrderItem;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.List;

public class DateOrderItemPanel extends BorderPane {
    private Order currentOrder;
    private Stage currentStage;
    private final Button backButton = new Button("Back");
    private ListView<OrderItem> orderItemListView = new ListView<>();
    private ObservableList<OrderItem> orderItemObservableList;
    private final Label saldoLabel=new Label();

    public DateOrderItemPanel(Order order,Stage stage){
        setTop(backButton);
        setCenter(orderItemListView);
        setOpaqueInsets(new Insets(10));
        setPadding(new Insets(10));

        currentOrder=order;
        currentStage=stage;
        orderItemObservableList = orderItemListView.getItems();
        List<OrderItem> orderList=currentOrder.getOrderItemList();
        orderItemObservableList.addAll(orderList);
        orderItemListView.setMaxHeight(300);
        backButton.setOnAction(this::onClickBackButon);
        saldoLabel.setText(getSaldo());
        saldoLabel.setFont(new Font(20));

        setBottom(saldoLabel);
    }

    private String getSaldo() {
        double saldo=0.;
        for (int i=0;i<orderItemObservableList.size();i++){
            OrderItem orderItem=orderItemObservableList.get(i);
            saldo+=orderItem.getUnitPrice()*orderItem.getQuantity();
        }
        return "Ukupno: "+saldo+"KM";
    }

    private void onClickBackButon(ActionEvent actionEvent) {
        LocalDate date = currentOrder.getOrderDate();
        DateOrderPanel dateOrderPanel=new DateOrderPanel(date,currentStage);
        Scene scene=new Scene(dateOrderPanel,500,400);
        currentStage.setScene(scene);
        currentStage.setTitle("Narudžbe");
        currentStage.show();
    }
}
