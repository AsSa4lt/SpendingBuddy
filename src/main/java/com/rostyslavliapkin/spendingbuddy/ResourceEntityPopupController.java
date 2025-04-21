package com.rostyslavliapkin.spendingbuddy;

import com.rostyslavliapkin.spendingbuddy.core.ResourceEntity;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Controller for the popup that allows editing or deleting a resource entity.
 * This class handles interactions for editing the name of a resource entity (e.g., Account, Income, Expense)
 * and deleting it, if necessary.
 */
public class ResourceEntityPopupController {
    /**
     * TextField for entering the name of the resource entity.
     */
    @FXML
    private TextField nameField;

    /**
     * The resource entity being edited in the popup.
     */
    private ResourceEntity entity;

    /**
     * Callback to run when the entity is deleted.
     */
    private Runnable onDeleteCallback;

    /**
     * Sets the resource entity to be edited and assigns a callback for deletion.
     *
     * @param entity the resource entity to be edited
     * @param onDeleteCallback a callback to be run when the entity is deleted
     */
    public void setEntity(ResourceEntity entity, Runnable onDeleteCallback) {
        this.entity = entity;
        this.onDeleteCallback = onDeleteCallback;

        nameField.setText(entity.getName());
    }

    /**
     * Handles the "Save" action. Validates the input name and saves it to the entity.
     * If the name is empty, an error alert is shown.
     */
    @FXML
    private void onSave() {
        String newName = nameField.getText();
        if (newName == null || newName.isBlank()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Name cannot be empty", ButtonType.OK);
            alert.show();
            return;
        }
        entity.setName(newName);
        nameField.getScene().getWindow().hide();
    }

    /**
     * Handles the "Delete" action. Runs the deletion callback if it's provided
     * and closes the popup window.
     */
    @FXML
    private void onDelete() {
        if (onDeleteCallback != null) {
            onDeleteCallback.run();
        }

        Stage stage = (Stage) nameField.getScene().getWindow();
        stage.close();
    }
}

