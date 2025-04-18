package com.rostyslavliapkin.spendingbuddy.controllers;

import com.rostyslavliapkin.spendingbuddy.core.commands.Command;
import com.rostyslavliapkin.spendingbuddy.managers.CommandsManager;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class AppController {
    private static CommandsManager commandsManager = new CommandsManager();
    public static void AddNewCommand(Command command){
        commandsManager.ExecuteCommand(command);
    }

    public static boolean UndoLastCommand(){
        return commandsManager.UndoLastCommand();
    }

    public static void HandleKeyEvents(KeyEvent event){
        if (event.getEventType() == KeyEvent.KEY_PRESSED && event.getCode() == KeyCode.Z && event.isControlDown()) {
            if (!UndoLastCommand()){
                Alert alert = new Alert(Alert.AlertType.WARNING, "Impossible to undo last command", ButtonType.OK);
                alert.show();
            }
        }
    }
}
