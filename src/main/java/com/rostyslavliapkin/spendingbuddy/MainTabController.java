package com.rostyslavliapkin.spendingbuddy;

import com.rostyslavliapkin.spendingbuddy.controllers.AccountsController;
import com.rostyslavliapkin.spendingbuddy.controllers.ExpensesController;
import com.rostyslavliapkin.spendingbuddy.controllers.IncomesController;
import com.rostyslavliapkin.spendingbuddy.core.Account;
import com.rostyslavliapkin.spendingbuddy.core.Expense;
import com.rostyslavliapkin.spendingbuddy.core.Income;
import com.rostyslavliapkin.spendingbuddy.utils.ViewGenerator;
import javafx.fxml.FXML;
import javafx.scene.layout.FlowPane;


public class MainTabController {

    @FXML
    private FlowPane incomesContainer;

    @FXML
    private FlowPane accountsContainer;

    @FXML
    private FlowPane expensesContainer;

    @FXML
    public void initialize() {
        for (Income income : IncomesController.GetIncomes()){
            incomesContainer.getChildren().add(ViewGenerator.createResourceEntityView(income));
        }
        for (Account account: AccountsController.GetAccounts()){
            accountsContainer.getChildren().add(ViewGenerator.createResourceEntityView(account));
        }

        for (Expense expense: ExpensesController.GetExpenses()){
            expensesContainer.getChildren().add(ViewGenerator.createResourceEntityView(expense));
        }
    }


}
