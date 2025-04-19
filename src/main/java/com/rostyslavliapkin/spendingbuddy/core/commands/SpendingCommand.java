package com.rostyslavliapkin.spendingbuddy.core.commands;

import com.rostyslavliapkin.spendingbuddy.core.Account;
import com.rostyslavliapkin.spendingbuddy.core.Expense;

import java.time.YearMonth;

public class SpendingCommand implements Command {
    private Account account;
    private Expense expense;
    private double amount;
    private YearMonth yearMonth;

    public SpendingCommand(Account account, Expense expense, double amount, YearMonth yearMonth){
        this.account = account;
        this.expense = expense;
        this.amount = amount;
        this.yearMonth = yearMonth;
    }

    @Override
    public boolean Execute() {
        return account.Spend(this) && expense.Spend(this);
    }

    @Override
    public boolean Undo() {
        return account.UndoSpend(this) && expense.UndoSpend(this);
    }

    public double GetAmount(){ return amount; }

    public YearMonth GetYearMonth() { return yearMonth; }
}
