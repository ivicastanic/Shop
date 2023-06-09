package com.shop.UI;

import com.shop.employee.Employee;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Controller {
    public static String PU_NAME="shopPU";
    private static Controller INSTANCE=null;
    private Stage mainStage;
    private static Employee currentEmployee;

    private Pane currentPane;

    private Controller(){
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
