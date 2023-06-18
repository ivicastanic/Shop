package com.shop.product.panel;

import com.shop.UI.Controller;
import com.shop.employee.Employee;
import com.shop.employee.privilege.Privilege;
import com.shop.employee.privilege.service.PrivilegeServiceLocal;
import com.shop.employee.service.EmployeeServiceLocal;
import com.shop.product.Product;
import com.shop.product.service.ProductServiceLocal;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;


public class AddProductPanel extends GridPane {
    private final TextField nameTextField = new TextField();
    private final Label nameLabel = new Label("Ime: ");
    private final TextField descriptionTextField = new TextField();
    private final Label descriptionLabel = new Label("Opis: ");
    private final TextField quantityTextField = new TextField();
    private final Label quantityLabel = new Label("Količina: ");
    private final TextField priceTextField = new TextField();
    private final Label priceLabel = new Label("Jedinična cijena: ");
    private final Button addProductButton = new Button("SAVE");

    public AddProductPanel() {
        setHgap(10);
        setVgap(10);
        setPadding(new Insets(20));

        setComponents();
        addComponents();
    }

    private void addComponents() {
        add(nameLabel, 0, 0);
        add(nameTextField, 1, 0);
        add(descriptionLabel, 0, 1);
        add(descriptionTextField, 1, 1);
        add(priceLabel, 0, 2);
        add(priceTextField, 1, 2);
        add(quantityLabel, 0, 3);
        add(quantityTextField, 1, 3);
        add(addProductButton, 0, 4);
    }

    private void setComponents() {
        //forma za dodavanje
        nameTextField.setMaxWidth(200);
        nameTextField.setPromptText("Enter name...");
        descriptionTextField.setMaxWidth(200);
        descriptionTextField.setPromptText("Enter description...");
        priceTextField.setMaxWidth(200);
        priceTextField.setPromptText("Enter price...");
        quantityTextField.setMaxWidth(200);
        quantityTextField.setPromptText("Enter quantity...");
        addProductButton.setOnAction(this::onClickAddProductButton);
    }

    private void onClickAddProductButton(ActionEvent actionEvent) {
        if (nameTextField.getText().isEmpty() || descriptionTextField.getText().isEmpty() ||
                priceTextField.getText().isEmpty() || quantityTextField.getText().isEmpty()) {
            Controller.instance().showDialog("Niste popunili sva polja!");
        } else {
            Product product = new Product();
            product.setName(nameTextField.getText());
            product.setDescription(descriptionTextField.getText());
            product.setPrice(Double.parseDouble(priceTextField.getText()));
            product.setQuantity(Integer.parseInt(quantityTextField.getText()));
            ProductServiceLocal.SERVICE.create(product);
            Controller.instance().getAddProductStage().close();
            Controller.instance().getProductsPanel().getProductObservableList().add(product);
        }
        clearTextField();
    }

    private void clearTextField() {
        nameTextField.clear();
        descriptionTextField.clear();
        priceTextField.clear();
        quantityTextField.clear();
    }
}
