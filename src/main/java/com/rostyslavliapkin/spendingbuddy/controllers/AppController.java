package com.rostyslavliapkin.spendingbuddy.controllers;

import com.rostyslavliapkin.spendingbuddy.MainTabController;
import com.rostyslavliapkin.spendingbuddy.core.Account;
import com.rostyslavliapkin.spendingbuddy.core.Expense;
import com.rostyslavliapkin.spendingbuddy.core.Income;
import com.rostyslavliapkin.spendingbuddy.core.commands.Command;
import com.rostyslavliapkin.spendingbuddy.managers.CommandsManager;
import com.rostyslavliapkin.spendingbuddy.utils.FileManager;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.time.YearMonth;

/**
 * Main controller fot the class that controls all events
 */
public class AppController {
    /**
     * Current selected year month that was selected by the user
     */
    public static YearMonth SelectedYearMonth;
    /**
     * Reference to MainTabController
     */
    public static MainTabController MainTab;
    /**
     * CommandManager to control commands and store all the data about the commands
     */
    private static CommandsManager commandsManager = new CommandsManager();

    /**
     * Function to execute a new Command
     * @param command to be executed
     */
    public static void ExecuteCommand(Command command){
        commandsManager.ExecuteCommand(command);
        FileManager.SaveToJson();
    }

    /**
     * Function to undo the last command
     * @return the success of undoing the command
     */
    public static boolean UndoLastCommand(){
        return commandsManager.UndoLastCommand();
    }

    /**
     * Function to remove a command from everywhere
     * @param command
     * @return a success of a result of removing the command
     */
    public static boolean RemoveCommand(Command command){
        AccountsController.RemoveCommand(command);
        ExpensesController.RemoveCommand(command);
        IncomesController.RemoveCommand(command);
        boolean result = commandsManager.RemoveCommand(command);
        UpdateSelectedMonth(SelectedYearMonth);
        return result;
    }

    /**
     * Function to handle the key input from user
     * @param event that happened
     */
    public static void HandleKeyEvents(KeyEvent event){
        if (event.getEventType() == KeyEvent.KEY_PRESSED && event.getCode() == KeyCode.Z && event.isControlDown()) {
            if (!UndoLastCommand()){
                Alert alert = new Alert(Alert.AlertType.WARNING, "Impossible to undo last command", ButtonType.OK);
                alert.show();
            }
        }
    }

    /**
     * Function to get a commands manager of the app controller
     * @return
     */
    public static CommandsManager GetCommandsManager(){
        return commandsManager;
    }

    /**
     * Function to update all values of our entities, when another YearMonth is selected
     * @param selectedYearMonth a new selected YearMonth
     */
    public static void UpdateSelectedMonth(YearMonth selectedYearMonth){
        SelectedYearMonth = selectedYearMonth;
        System.out.println(SelectedYearMonth);
        for (Income income: IncomesController.GetIncomes()){
            income.UpdateFromYearMonth(SelectedYearMonth);
        }
        for (Account account: AccountsController.GetAccounts()){
            account.UpdateFromYearMonth(SelectedYearMonth);
        }
        for (Expense expense: ExpensesController.GetExpenses()){
            expense.UpdateFromYearMonth(SelectedYearMonth);
        }
    }

    /**
     * Function to create a default resource entities if there are no user entities
     */
    public static void CreateDefaultResourceEntities(){
        AccountsController.CreateDefaultAccounts();
        ExpensesController.CreateDefaultExpenses();
        IncomesController.CreateDefaultIncomes();
    }
}
