package com.rostyslavliapkin.spendingbuddy.utils;

import com.rostyslavliapkin.spendingbuddy.MainController;
import com.rostyslavliapkin.spendingbuddy.ResourceEntityPopupController;
import com.rostyslavliapkin.spendingbuddy.controllers.*;
import com.rostyslavliapkin.spendingbuddy.core.Account;
import com.rostyslavliapkin.spendingbuddy.core.Expense;
import com.rostyslavliapkin.spendingbuddy.core.Income;
import com.rostyslavliapkin.spendingbuddy.core.ResourceEntity;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;
import javafx.scene.image.WritableImage;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Optional;

/**
 * Utility class responsible for generating visual representations of {@link ResourceEntity} objects,
 * such as accounts, incomes, and expenses. Provides a UI component with integrated
 * drag-and-drop functionality, allowing users to perform actions.
 */
public class ViewGenerator {

    /**
     * Generates a JavaFX VBox view for a given {@link ResourceEntity}.
     * Also defines behavior for clicks, drag and drop, etc.
     * @param entity The {@link ResourceEntity} to generate a view for
     * @return A fully configured VBox component representing the entity
     */
    public static VBox CreateResourceEntityView(ResourceEntity entity) {
        ImageView imageView = new ImageView(new Image(entity.getImageUrl().toExternalForm()));
        imageView.setFitWidth(35);
        imageView.setFitHeight(35);
        imageView.setPreserveRatio(true);

        Label nameLabel = new Label();
        nameLabel.setFont(new Font(10));
        nameLabel.textProperty().bind(Bindings.createStringBinding(() ->
                entity.nameProperty().get(),
                entity.nameProperty()
        ));

        Label amountLabel = new Label();
        amountLabel.setFont(new Font(10));
        DecimalFormat df = new DecimalFormat("#000.00#");
        amountLabel.textProperty().bind(Bindings.createStringBinding(() ->
                String.format("%.2f " + SettingsController.SelectedCurrency, entity.getValue()),
                entity.valueProperty()
        ));

        VBox box = new VBox(5, nameLabel, imageView,  amountLabel);
        box.setStyle("-fx-alignment: center; -fx-padding: 10; -fx-border-color: gray; -fx-border-width: 1;");
        box.setUserData(entity); // store account reference for later use
        box.setMinWidth(90);
        box.setMaxWidth(90);
        box.setMinHeight(90);
        box.setMaxHeight(90);


        // 1. Start Drag
        // in case if it's a spending, we can't drag and drop it, so we don't need this feature.
        if (entity.GetType() != ResourceEntity.EntityType.EXPENSE) {
            box.setOnDragDetected(event -> {
                Dragboard db = box.startDragAndDrop(TransferMode.MOVE);

                ClipboardContent content = new ClipboardContent();
                content.putString(entity.getName());
                db.setContent(content);

                WritableImage snapshot = box.snapshot(null, null);
                db.setDragView(snapshot);

                event.consume();
            });
        }

        // 2. Accept drag over
        box.setOnDragOver(event -> {
            if (event.getGestureSource() != box && event.getDragboard().hasString()) {
                event.acceptTransferModes(TransferMode.MOVE);
            }
            event.consume();
        });

        // 3. Visual Feedback
        box.setOnDragEntered(event -> {
            if (event.getGestureSource() != box && event.getDragboard().hasString()) {
                box.setStyle(box.getStyle() + "; -fx-border-color: green; -fx-border-width: 2;");
            }
        });

        box.setOnDragExited(event -> {
            box.setStyle("-fx-alignment: center; -fx-padding: 10; -fx-border-color: gray; -fx-border-width: 1;");
        });

        // 4. Drop action
        box.setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();
            boolean success = false;

            if (db.hasString()) {
                VBox sourceBox = (VBox) event.getGestureSource();
                ResourceEntity sourceEntity = (ResourceEntity) sourceBox.getUserData();
                ResourceEntity targetEntity = (ResourceEntity) box.getUserData();

                // there are few possible combinations of types that can work for us
                try {
                    if (sourceEntity.GetType() == ResourceEntity.EntityType.INCOME && targetEntity.GetType() == ResourceEntity.EntityType.ACCOUNT) {
                        // if source is Income and target is Account, so we are trying to deposit money
                        CommandGenerator.CreateDepositCommand((Income) sourceEntity, (Account) targetEntity);
                    } else if (sourceEntity.GetType() == ResourceEntity.EntityType.ACCOUNT && targetEntity.GetType() == ResourceEntity.EntityType.ACCOUNT){
                        // if source and target are Account, so we are transferring money from one account to another
                        CommandGenerator.CreateTransferBetweenAccountsCommand((Account) sourceEntity, (Account) targetEntity);
                    }else if (sourceEntity.GetType() == ResourceEntity.EntityType.ACCOUNT && targetEntity.GetType() == ResourceEntity.EntityType.EXPENSE){
                        // if source is Account and target is Spending, so we are spending money on something
                        CommandGenerator.CreateSpendingCommand((Account) sourceEntity, (Expense) targetEntity);
                    }
                } catch (Exception e){
                    Alert alert = new Alert(Alert.AlertType.ERROR, "You entered invalid value", ButtonType.OK);
                }


                success = true;
            }

            event.setDropCompleted(success);
            event.consume();
        });

        box.setOnMouseClicked(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(MainController.class.getResource("resource_popup.fxml"));
                Parent root = loader.load();

                ResourceEntityPopupController controller = loader.getController();
                ResourceEntity entityR = (ResourceEntity) box.getUserData();
                controller.setEntity(entity, () -> {
                    Alert alert = new Alert(Alert.AlertType.WARNING, "Events related to this category are going to be deleted", ButtonType.OK, ButtonType.NO);
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.isPresent() && result.get() == ButtonType.OK) {
                        if (entityR.GetType() == ResourceEntity.EntityType.ACCOUNT) {
                            AccountsController.RemoveAccount((Account) entityR);
                        } else if (entityR.GetType() == ResourceEntity.EntityType.INCOME) {
                            IncomesController.RemoveIncome((Income) entityR);
                        } else if (entityR.GetType() == ResourceEntity.EntityType.EXPENSE) {
                            ExpensesController.RemoveExpense((Expense) entityR);
                        }
                        AppController.GetCommandsManager().RemoveEntity(entityR);
                        AppController.UpdateSelectedMonth(AppController.SelectedYearMonth);
                        AppController.MainTab.UpdateView();
                        FileManager.SaveToJson();
                    }
                });

                Stage stage = new Stage();
                stage.setTitle("Entity Details");
                stage.setScene(new Scene(root));
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.showAndWait();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });



        return box;
    }

}
