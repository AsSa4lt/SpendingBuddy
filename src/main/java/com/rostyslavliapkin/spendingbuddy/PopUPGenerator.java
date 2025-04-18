package com.rostyslavliapkin.spendingbuddy;

import com.rostyslavliapkin.spendingbuddy.controllers.AccountsController;
import com.rostyslavliapkin.spendingbuddy.controllers.ExpensesController;
import com.rostyslavliapkin.spendingbuddy.controllers.IncomesController;
import com.rostyslavliapkin.spendingbuddy.core.Account;
import com.rostyslavliapkin.spendingbuddy.core.Expense;
import com.rostyslavliapkin.spendingbuddy.core.Income;
import com.rostyslavliapkin.spendingbuddy.core.ResourceEntity;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Objects;

public class PopUPGenerator {
    public static void ShowAddEntityPopup(String title, ResourceEntity.EntityType type) throws URISyntaxException, MalformedURLException {
        Stage popup = new Stage();
        popup.setTitle("Add New Entity");

        VBox root = new VBox(10);
        root.setPadding(new Insets(15));

        Label nameLabel = new Label("Enter name:");
        TextField nameField = new TextField();

        Label imageLabel = new Label("Choose an image:");
        FlowPane imagePicker = new FlowPane(10, 10);

        // Load images from /images/
        File imagesDir = new File(Objects.requireNonNull(PopUPGenerator.class.getResource("/images/")).toURI());
        ToggleGroup toggleGroup = new ToggleGroup();

        for (File file : Objects.requireNonNull(imagesDir.listFiles())) {
            if (!file.getName().endsWith(".png") || file.getName().endsWith("plus.png")) continue;

            ImageView imgView = new ImageView(new Image(file.toURI().toString()));
            imgView.setFitWidth(64);
            imgView.setFitHeight(64);

            ToggleButton imgButton = new ToggleButton();
            imgButton.setGraphic(imgView);
            imgButton.setToggleGroup(toggleGroup);
            imgButton.setUserData(file.toURI().toURL()); // store image URL

            imagePicker.getChildren().add(imgButton);
        }

        Button confirm = new Button("Add");
        confirm.setOnAction(event -> {
            String name = nameField.getText();
            Toggle selected = toggleGroup.getSelectedToggle();
            if (name.isEmpty() || selected == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter a name and select an image.", ButtonType.OK);
                alert.showAndWait();
                return;
            }

            URL selectedImage = (URL) selected.getUserData();
            if (type == ResourceEntity.EntityType.INCOME){
                IncomesController.AddNewIncome(new Income(name, selectedImage));
            }else if (type == ResourceEntity.EntityType.ACCOUNT){
                AccountsController.AddNewAccount(new Account(name, selectedImage));
            }else if (type == ResourceEntity.EntityType.EXPENSE){
                ExpensesController.AddNewExpense(new Expense(name, selectedImage))  ;
            }
            popup.close();
        });

        root.getChildren().addAll(nameLabel, nameField, imageLabel, imagePicker, confirm);
        popup.setScene(new Scene(root));
        // to block background
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.showAndWait();
    }
}
