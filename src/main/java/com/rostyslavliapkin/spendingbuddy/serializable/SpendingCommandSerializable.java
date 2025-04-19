package com.rostyslavliapkin.spendingbuddy.serializable;

import com.rostyslavliapkin.spendingbuddy.controllers.AccountsController;
import com.rostyslavliapkin.spendingbuddy.controllers.ExpensesController;
import com.rostyslavliapkin.spendingbuddy.core.Account;
import com.rostyslavliapkin.spendingbuddy.core.Expense;
import com.rostyslavliapkin.spendingbuddy.core.commands.SpendingCommand;

import java.time.YearMonth;

public class SpendingCommandSerializable {
    public String AccountName;
    public String ExpenseName;
    public double Amount;
    public YearMonth CommandYearMonth;

    public SpendingCommandSerializable() {}

    public SpendingCommandSerializable(SpendingCommand command){
        AccountName = command.GetAccount().getName();
        ExpenseName = command.GetExpense().getName();
        Amount = command.GetAmount();
        CommandYearMonth = command.GetYearMonth();
    }

    public SpendingCommand ToSpendingCommand(){
        Account account = AccountsController.GetAccountByName(AccountName);
        Expense expense = ExpensesController.GetExpenseByName(ExpenseName);
        return new SpendingCommand(account, expense, Amount, CommandYearMonth);
    }
}
