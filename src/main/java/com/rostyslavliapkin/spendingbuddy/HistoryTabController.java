package com.rostyslavliapkin.spendingbuddy;

import com.rostyslavliapkin.spendingbuddy.controllers.AppController;
import com.rostyslavliapkin.spendingbuddy.controllers.SettingsController;
import com.rostyslavliapkin.spendingbuddy.core.commands.Command;
import com.rostyslavliapkin.spendingbuddy.managers.CommandsManager;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class HistoryTabController {

    @FXML
    private VBox historyContainer;

    private CommandsManager commandsManager;

    public void SetCommandsManager(CommandsManager manager) {
        this.commandsManager = manager;
        renderHistory();
    }

    public void renderHistory() {
        historyContainer.getChildren().clear();

        if (commandsManager == null) return;

        for (Map.Entry<LocalDate, List<Command>> entry : commandsManager.allCommands.entrySet()) {
            LocalDate date = entry.getKey();
            List<Command> commands = entry.getValue();

            String formattedDate = String.format("📅 %s %d, %d",
                    date.getMonth().name().substring(0, 1).toUpperCase() + date.getMonth().name().substring(1).toLowerCase(),
                    date.getDayOfMonth(),
                    date.getYear());

            Label dayLabel = new Label(formattedDate);
            dayLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
            historyContainer.getChildren().add(dayLabel);

            // Reverse the list before rendering
            List<Command> reversed = new ArrayList<>(commands);
            Collections.reverse(reversed);

            for (Command command : reversed) {
                // Left side: image + description
                ImageView imageView = new ImageView(command.GetImageURL().toExternalForm());
                imageView.setFitWidth(24);
                imageView.setFitHeight(24);

                Label descriptionLabel = new Label(command.GetDescription());
                descriptionLabel.setStyle("-fx-font-size: 13px;");

                HBox leftBox = new HBox(10, imageView, descriptionLabel);
                leftBox.setAlignment(Pos.CENTER_LEFT);

                // Right side: amount
                Label amountLabel = new Label(String.format("%.2f " + SettingsController.SelectedCurrency, command.GetAmount()));
                amountLabel.setStyle("-fx-font-size: 13px; -fx-font-weight: bold;");

                // Delete button
                javafx.scene.control.Button deleteButton = new javafx.scene.control.Button("🗑");
                deleteButton.setStyle("-fx-background-color: transparent; -fx-font-size: 14px;");
                deleteButton.setOnAction(e -> {
                    javafx.scene.control.Alert alert = new javafx.scene.control.Alert(
                            javafx.scene.control.Alert.AlertType.CONFIRMATION,
                            "Are you sure you want to delete this command?",
                            javafx.scene.control.ButtonType.YES,
                            javafx.scene.control.ButtonType.NO
                    );
                    alert.setHeaderText(null);
                    alert.showAndWait().ifPresent(response -> {
                        if (response == javafx.scene.control.ButtonType.YES) {
                            AppController.RemoveCommand(command);
                            renderHistory();
                        }
                    });
                });

                Region spacer = new Region();
                HBox.setHgrow(spacer, Priority.ALWAYS);

                HBox row = new HBox(10, leftBox, spacer, amountLabel, deleteButton);
                row.setPadding(new Insets(4, 0, 4, 0));
                row.setAlignment(Pos.CENTER);

                historyContainer.getChildren().add(row);
            }

        }
    }

}
