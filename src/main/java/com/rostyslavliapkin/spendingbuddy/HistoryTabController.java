package com.rostyslavliapkin.spendingbuddy;

import com.rostyslavliapkin.spendingbuddy.controllers.SettingsController;
import com.rostyslavliapkin.spendingbuddy.core.commands.Command;
import com.rostyslavliapkin.spendingbuddy.core.commands.DepositCommand;
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

import java.net.URL;
import java.time.MonthDay;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

        for (Map.Entry<MonthDay, List<Command>> entry : commandsManager.allCommands.entrySet()) {
            MonthDay day = entry.getKey();
            List<Command> commands = entry.getValue();

            Label dayLabel = new Label("📅 " + day.getMonth() + " " + day.getDayOfMonth());
            dayLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
            historyContainer.getChildren().add(dayLabel);

            for (Command cmd : commands) {
                if (cmd instanceof DepositCommand) {
                    DepositCommand command = (DepositCommand) cmd;

                    // Left side: image + description
                    ImageView imageView = new ImageView(command.GetImageURL().toExternalForm());
                    imageView.setFitWidth(24);
                    imageView.setFitHeight(24);

                    Label descriptionLabel = new Label(command.GetDescription());
                    descriptionLabel.setStyle("-fx-font-size: 13px;");

                    // Align image and description vertically
                    HBox leftBox = new HBox(10, imageView, descriptionLabel);
                    leftBox.setAlignment(Pos.CENTER_LEFT);  // vertically center

                    // Right side: amount
                    Label amountLabel = new Label(String.format("%.2f " + SettingsController.SelectedCurrency, command.GetAmount()));
                    amountLabel.setStyle("-fx-font-size: 13px; -fx-font-weight: bold;");

                    // Spacer between left and right
                    Region spacer = new Region();
                    HBox.setHgrow(spacer, Priority.ALWAYS);

                    // Full row
                    HBox row = new HBox(10, leftBox, spacer, amountLabel);
                    row.setPadding(new Insets(4, 0, 4, 0));
                    row.setAlignment(Pos.CENTER); // 👈 this ensures all children are vertically centered

                    historyContainer.getChildren().add(row);
                }

                // You can add other Command types like SpendingCommand or TransferCommand here the same way.
            }
        }
    }

}
