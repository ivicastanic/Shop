package com.shop.UI;

import com.shop.customer.panel.CustomersPanel;
import com.shop.employee.Employee;
import com.shop.employee.panel.EmployeePanel;
import com.shop.product.panel.ProductsPanel;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Controller {
    public static String PU_NAME="shopPU";
    private static Controller INSTANCE=null;
    private Stage mainStage;
    private Stage addEmployeeStage=new Stage();
    private Stage addCustomerStage=new Stage();
    private Stage addProductStage=new Stage();
    private static Employee currentEmployee;

    private EmployeePanel employeePanel;
    private CustomersPanel customersPanel;
    private ProductsPanel productsPanel;

    private Controller(){
    }

    public void showDialog(String greška){
        Dialog dialog = new Dialog<>();
        dialog.setTitle("Greška");
        dialog.setContentText(greška);
        dialog.show();
        dialog.setHeight(150);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL);
    }

    public Stage getAddProductStage() {
        return addProductStage;
    }

    public void setAddProductStage(Stage addProductStage) {
        this.addProductStage = addProductStage;
    }

    public ProductsPanel getProductsPanel() {
        return productsPanel;
    }

    public void setProductsPanel(ProductsPanel productsPanel) {
        this.productsPanel = productsPanel;
    }

    public CustomersPanel getCustomersPanel() {
        return customersPanel;
    }

    public void setCustomersPanel(CustomersPanel customersPanel) {
        this.customersPanel = customersPanel;
    }

    public Stage getAddCustomerStage() {
        return addCustomerStage;
    }

    public void setAddCustomerStage(Stage addCustomerStage) {
        this.addCustomerStage = addCustomerStage;
    }

    public EmployeePanel getEmployeePanel() {
        return employeePanel;
    }

    public void setEmployeePanel(EmployeePanel employeePanel) {
        this.employeePanel = employeePanel;
    }

    public Stage getAddEmployeeStage() {
        return addEmployeeStage;
    }

    public void setAddEmployeeStage(Stage addEmployeeStage) {
        this.addEmployeeStage = addEmployeeStage;
    }

    public static Employee getCurrentEmployee() {
        return currentEmployee;
    }
    public static Label getCurrentEmployeeLabel(){
        Label currentEmployeeLabel=new Label();
        currentEmployeeLabel.setText(Controller.getCurrentEmployee().getName() + ", " + Controller.getCurrentEmployee().getSurname());
        return currentEmployeeLabel;
    }

    public static void setCurrentEmployee(Employee currentEmployee) {
        Controller.currentEmployee = currentEmployee;
    }

    public Stage getMainStage() {
        return mainStage;
    }

    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
    }

    public static Controller instance(){
        if(INSTANCE==null){
            INSTANCE=new Controller();
        }
        return INSTANCE;
    }


}
