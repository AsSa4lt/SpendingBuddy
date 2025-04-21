package com.rostyslavliapkin.spendingbuddy;

import com.rostyslavliapkin.spendingbuddy.controllers.AppController;
import com.rostyslavliapkin.spendingbuddy.controllers.SettingsController;
import com.rostyslavliapkin.spendingbuddy.utils.FileManager;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Button;

import java.util.Optional;

/**
 * Controller for the settings tab where the user can modify application settings,
 * such as currency and data deletion.
 */
public class SettingsTabController {
    /**
     * ComboBox for selecting the currency symbol.
     */
    @FXML
    private ComboBox<String> currencyComboBox;

    /**
     * Button to delete all user data.
     */
    @FXML
    private Button deleteDataButton;

    /**
     * Initializes the settings tab by populating the currency ComboBox and setting up the event listeners.
     */
    @FXML
    private void initialize() {
        currencyComboBox.getItems().addAll("$", "€", "£", "Kč", "₴", "¥");
        currencyComboBox.setValue("$");

        currencyComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                SettingsController.SelectedCurrency = newValue;
                AppController.MainTab.UpdateView();
                FileManager.SaveToJson();
            }
        });

        deleteDataButton.setOnAction(event -> handleDeleteData());
    }

    /**
     * Handles the deletion of all user data. Prompts the user with a confirmation dialog,
     * and deletes the data if confirmed.
     */
    private void handleDeleteData() {
        // Create a confirmation dialog
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Delete Data");
        confirmationAlert.setHeaderText("Are you sure you want to delete all user data?");
        confirmationAlert.setContentText("This action is irreversible and will remove all saved data.");

        // Show the dialog and wait for the user's response
        Optional<ButtonType> result = confirmationAlert.showAndWait();

        // If the user clicks "OK", delete the data
        if (result.isPresent() && result.get() == ButtonType.OK) {
            boolean success = FileManager.DeleteDataFile();
            if (success) {
                // Inform the user that the data has been deleted
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Data Deleted");
                successAlert.setHeaderText("User data deleted successfully.");
                successAlert.showAndWait();
                AppController.MainTab.UpdateView();
                AppController.CreateDefaultResourceEntities();
            } else {
                // Inform the user that the deletion failed
                Alert failureAlert = new Alert(Alert.AlertType.ERROR);
                failureAlert.setTitle("Deletion Failed");
                failureAlert.setHeaderText("Failed to delete user data.");
                failureAlert.showAndWait();
            }
        } else {
            // User canceled the deletion
            System.out.println("Data deletion canceled.");
        }
    }
}
