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

/**
 * Entry point of the Spending Buddy application.
 * Initializes the UI and loads persisted user data from a JSON file.
 * If loading fails, default resources and entities are created.
 */
public class MainApp extends Application {

    /**
     * Initializes and displays the primary stage (main window) of the application.
     * Loads user data from JSON or resets to default state on failure.
     *
     * @param stage The primary stage for this application, onto which the application scene is set.
     * @throws Exception If there is an issue during FXML loading or application setup.
     */
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

    /**
     * Main method. Launches the JavaFX application.
     *
     * @param args The command line arguments.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
