package com.rostyslavliapkin.spendingbuddy.utils;

import com.rostyslavliapkin.spendingbuddy.controllers.AppController;
import com.rostyslavliapkin.spendingbuddy.core.Account;
import com.rostyslavliapkin.spendingbuddy.core.Expense;
import com.rostyslavliapkin.spendingbuddy.core.Income;
import com.rostyslavliapkin.spendingbuddy.core.ResourceEntity;
import com.rostyslavliapkin.spendingbuddy.core.commands.DepositCommand;
import com.rostyslavliapkin.spendingbuddy.core.commands.SpendingCommand;
import com.rostyslavliapkin.spendingbuddy.core.commands.TransferCommand;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;

import java.util.Optional;

public class CommandGenerator {
    public static double GetCommandAmount(ResourceEntity sourceEntity, ResourceEntity targetEntity) throws Exception {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Transfer Amount");
        dialog.setHeaderText("Transfer from " + sourceEntity.getName() + " to " + targetEntity.getName());
        dialog.setContentText("Enter amount:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            try {
                return Double.parseDouble(result.get());
            } catch (NumberFormatException e) {
                throw new Exception("Invalid number input: " + result.get());
            }
        } else {
            throw new Exception("No value entered.");
        }
    }


    public static void CreateDepositCommand(Income income, Account account) throws Exception {
        double amount = GetCommandAmount(income, account);
        if (amount <= 0){
            Alert alert = new Alert(Alert.AlertType.ERROR, "You can enter only a positive value", ButtonType.OK);
        }
        DepositCommand depositCommand = new DepositCommand(income, account, amount, AppController.SelectedYearMonth);
        AppController.AddNewCommand(depositCommand);
    }

    public static void CreateTransferBetweenAccountsCommand(Account sourceAccount, Account targetAccount) throws Exception {
        double amount = GetCommandAmount(sourceAccount, targetAccount);
        TransferCommand transferCommand = new TransferCommand(sourceAccount, targetAccount, amount, AppController.SelectedYearMonth);
        AppController.AddNewCommand(transferCommand);
    }

    public static void CreateSpendingCommand(Account account, Expense expense) throws  Exception {
        double amount = GetCommandAmount(account, expense);
        SpendingCommand spendingCommand = new SpendingCommand(account, expense, amount, AppController.SelectedYearMonth);
        AppController.AddNewCommand(spendingCommand);
    }
}
