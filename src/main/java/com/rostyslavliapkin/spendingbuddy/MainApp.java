package com.rostyslavliapkin.spendingbuddy;

import com.rostyslavliapkin.spendingbuddy.controllers.AccountsController;
import com.rostyslavliapkin.spendingbuddy.controllers.AppController;
import com.rostyslavliapkin.spendingbuddy.controllers.ExpensesController;
import com.rostyslavliapkin.spendingbuddy.controllers.IncomesController;
import com.rostyslavliapkin.spendingbuddy.utils.FileManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.util.Objects;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        try{
            FileManager.LoadFromJson();
        }catch (Exception e){
            AccountsController.ClearAccounts();
            IncomesController.ClearIncomes();
            ExpensesController.ClearExpenses();
            AppController.GetCommandsManager().allCommands.clear();
            AppController.CreateDefaultResourceEntities();
        }


        Scene scene = new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("main_view.fxml"))));
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
