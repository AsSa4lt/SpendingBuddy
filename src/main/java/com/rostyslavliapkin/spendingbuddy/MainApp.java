package com.rostyslavliapkin.spendingbuddy;

import com.rostyslavliapkin.spendingbuddy.controllers.AppController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        AppController.CreateDefaultResourceEntities();
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("main_view.fxml")));
        scene.addEventHandler(KeyEvent.ANY, (AppController::HandleKeyEvents));
        stage.setScene(scene);
        stage.setTitle("Spending Buddy");
        stage.setWidth(800);
        stage.setMinWidth(700);
        stage.setHeight(700);
        stage.setMinHeight(600);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
