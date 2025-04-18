package com.rostyslavliapkin.spendingbuddy.core.commands;

import com.rostyslavliapkin.spendingbuddy.core.Account;
import com.rostyslavliapkin.spendingbuddy.core.Spending;

public class SpendingCommand implements Command {
    private Account account;
    private Spending spending;
    private double amount;

    public SpendingCommand(Account account, Spending spending, double amount){
        this.account = account;
        this.spending = spending;
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
}
