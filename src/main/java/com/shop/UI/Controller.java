package com.shop.UI;

import javafx.stage.Stage;

public class Controller {
    public static String PU_NAME="shopPU";
    private static Controller INSTANCE=null;
    private Stage mainStage;

    private Controller(){
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
