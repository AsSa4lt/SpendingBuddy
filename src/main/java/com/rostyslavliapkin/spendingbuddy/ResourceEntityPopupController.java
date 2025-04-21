package com.rostyslavliapkin.spendingbuddy;

import com.rostyslavliapkin.spendingbuddy.core.ResourceEntity;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ResourceEntityPopupController {

    @FXML
    private TextField nameField;

    private ResourceEntity entity;
    private Runnable onDeleteCallback;

    public void setEntity(ResourceEntity entity, Runnable onDeleteCallback) {
        this.entity = entity;
        this.onDeleteCallback = onDeleteCallback;

        nameField.setText(entity.getName());
    }

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

    @FXML
    private void onDelete() {
        if (onDeleteCallback != null) {
            onDeleteCallback.run();
        }

        Stage stage = (Stage) nameField.getScene().getWindow();
        stage.close();
    }
}

