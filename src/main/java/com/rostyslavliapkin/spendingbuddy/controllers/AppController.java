package com.rostyslavliapkin.spendingbuddy.controllers;

import com.rostyslavliapkin.spendingbuddy.core.commands.Command;
import com.rostyslavliapkin.spendingbuddy.managers.CommandsManager;

public class AppController {
    private static CommandsManager commandsManager = new CommandsManager();
    public static void AddNewCommand(Command command){
        commandsManager.ExecuteCommand(command);
    }
}
