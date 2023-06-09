package com.shop;

import com.shop.UI.Controller;
import com.shop.UI.paneli.LoginPanel;
import javafx.application.Application;

import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class StartApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Controller.instance().setMainStage(stage);
        LoginPanel loginPanel=new LoginPanel();
        Scene scene = new Scene(loginPanel);
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}