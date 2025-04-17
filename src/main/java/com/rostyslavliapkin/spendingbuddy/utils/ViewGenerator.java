package com.rostyslavliapkin.spendingbuddy.utils;

import com.rostyslavliapkin.spendingbuddy.core.Account;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.VBox;

import java.text.DecimalFormat;

public class ViewGenerator {
    public static VBox createAccountView(Account account) {
        ImageView imageView = new ImageView(new Image(account.GetImageUrl().toExternalForm()));
        imageView.setFitWidth(64);
        imageView.setFitHeight(64);
        imageView.setPreserveRatio(true);

        Label nameLabel = new Label(account.GetName());
        Label amountLabel = new Label(new DecimalFormat("#0.00#").format(account.GetValue()));

        VBox box = new VBox(5, nameLabel, imageView,  amountLabel);
        box.setStyle("-fx-alignment: center; -fx-padding: 10; -fx-border-color: gray; -fx-border-width: 1;");
        box.setUserData(account); // store account reference for later use

        // 1. Start Drag
        box.setOnDragDetected(event -> {
            Dragboard db = box.startDragAndDrop(TransferMode.MOVE);
            ClipboardContent content = new ClipboardContent();
            content.putString(account.GetName());
            db.setContent(content);
            event.consume();
        });

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
                box.setStyle("-fx-border-color: green; -fx-border-width: 2;");
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
                Account sourceAccount = (Account) sourceBox.getUserData();
                Account targetAccount = (Account) box.getUserData();

                // Example: just swap names for now
                String temp = sourceAccount.GetName();
                sourceAccount = new Account(targetAccount.GetName(), targetAccount.GetImageUrl());
                targetAccount = new Account(temp, sourceAccount.GetImageUrl());

                // TODO: Something to happen
                System.out.println("Dropped from " + sourceAccount.GetName() + " to " + targetAccount.GetName());

                success = true;
            }
            event.setDropCompleted(success);
            event.consume();
        });

        return box;
    }

}
