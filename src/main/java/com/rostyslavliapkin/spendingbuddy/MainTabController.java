package com.rostyslavliapkin.spendingbuddy;

import com.rostyslavliapkin.spendingbuddy.controllers.AccountsController;
import com.rostyslavliapkin.spendingbuddy.core.Account;
import com.rostyslavliapkin.spendingbuddy.utils.ViewGenerator;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class MainTabController {

    @FXML
    private HBox incomeContainer;

    @FXML
    private HBox accountsContainer;

    @FXML
    private VBox expensesContainer;

    @FXML
    public void initialize() {
        addIncomeItem("Salary Lucky - 900000$");
        // Example usage
        for(Account account: AccountsController.GetAccounts()){
            accountsContainer.getChildren().add(ViewGenerator.createAccountView(account));
        }
        addExpenseItem("Groceries - $120");
    }

    public void addIncomeItem(String incomeDescription) {
        Label incomeLabel = new Label(incomeDescription);
        incomeContainer.getChildren().add(incomeLabel);
    }

    public void addAccountItem(String accountName) {
        Label accountLabel = new Label(accountName);
        accountsContainer.getChildren().add(accountLabel);
    }

    public void addExpenseItem(String expenseDescription) {
        Label expenseLabel = new Label(expenseDescription);
        expensesContainer.getChildren().add(expenseLabel);
    }
}
