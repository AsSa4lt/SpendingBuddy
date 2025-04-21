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

public class AppController {
    public static YearMonth SelectedYearMonth;
    public static MainTabController MainTab;
    private static CommandsManager commandsManager = new CommandsManager();
    public static void AddNewCommand(Command command){
        commandsManager.ExecuteCommand(command);
        FileManager.SaveToJson();
    }

    public static boolean UndoLastCommand(){
        return commandsManager.UndoLastCommand();
    }

    public static boolean RemoveCommand(Command command){
        AccountsController.RemoveCommand(command);
        ExpensesController.RemoveCommand(command);
        IncomesController.RemoveCommand(command);
        boolean result = commandsManager.RemoveCommand(command);
        UpdateSelectedMonth(SelectedYearMonth);
        return result;
    }

    public static void HandleKeyEvents(KeyEvent event){
        if (event.getEventType() == KeyEvent.KEY_PRESSED && event.getCode() == KeyCode.Z && event.isControlDown()) {
            if (!UndoLastCommand()){
                Alert alert = new Alert(Alert.AlertType.WARNING, "Impossible to undo last command", ButtonType.OK);
                alert.show();
            }
        }
    }

    public static CommandsManager GetCommandsManager(){
        return commandsManager;
    }

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

    public static void CreateDefaultResourceEntities(){
        AccountsController.CreateDefaultAccounts();
        ExpensesController.CreateDefaultExpenses();
        IncomesController.CreateDefaultIncomes();
    }
}
