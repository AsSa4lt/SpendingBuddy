package com.rostyslavliapkin.spendingbuddy.core.commands;

import com.rostyslavliapkin.spendingbuddy.core.Account;
import com.rostyslavliapkin.spendingbuddy.core.Expense;

public class SpendingCommand implements Command {
    private Account account;
    private Expense expense;
    private double amount;

    public SpendingCommand(Account account, Expense expense, double amount){
        this.account = account;
        this.expense = expense;
        this.amount = amount;
    }

    @Override
    public boolean Execute() {
        return true;
    }

    @Override
    public boolean Undo() {
        return true;
    }

    public double GetAmount(){ return amount; }

}
