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

/**
 * Utility class for creating and executing commands based on user input.
 * Includes logic for generating {@link DepositCommand}, {@link TransferCommand}, and {@link SpendingCommand}.
 */
public class CommandGenerator {
    /**
     * Prompts the user to enter a monetary amount for a command involving two resource entities.
     *
     * @param sourceEntity the source entity involved in the transaction
     * @param targetEntity the target entity involved in the transaction
     * @return the amount entered by the user
     * @throws Exception if the input is invalid or the user cancels the dialog
     */
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

    /**
     * Creates a {@link DepositCommand} using user input and executes it via {@link AppController}.
     *
     * @param income  the income source to deposit from
     * @param account the account to deposit into
     * @throws Exception if the user cancels input or enters invalid data
     */
    public static void CreateDepositCommand(Income income, Account account) throws Exception {
        double amount = GetCommandAmount(income, account);
        if (amount <= 0){
            Alert alert = new Alert(Alert.AlertType.ERROR, "You can enter only a positive value", ButtonType.OK);
        }
        DepositCommand depositCommand = new DepositCommand(income, account, amount, AppController.SelectedYearMonth);
        AppController.ExecuteCommand(depositCommand);
    }

    /**
     * Creates a {@link TransferCommand} between two accounts and executes it.
     *
     * @param sourceAccount the account to transfer from
     * @param targetAccount the account to transfer to
     * @throws Exception if the user cancels input or enters invalid data
     */
    public static void CreateTransferBetweenAccountsCommand(Account sourceAccount, Account targetAccount) throws Exception {
        double amount = GetCommandAmount(sourceAccount, targetAccount);
        TransferCommand transferCommand = new TransferCommand(sourceAccount, targetAccount, amount, AppController.SelectedYearMonth);
        AppController.ExecuteCommand(transferCommand);
    }

    /**
     * Creates a {@link SpendingCommand} from an account to an expense category and executes it.
     *
     * @param account the account to spend from
     * @param expense the expense category
     * @throws Exception if the user cancels input or enters invalid data
     */
    public static void CreateSpendingCommand(Account account, Expense expense) throws  Exception {
        double amount = GetCommandAmount(account, expense);
        SpendingCommand spendingCommand = new SpendingCommand(account, expense, amount, AppController.SelectedYearMonth);
        AppController.ExecuteCommand(spendingCommand);
    }
}
