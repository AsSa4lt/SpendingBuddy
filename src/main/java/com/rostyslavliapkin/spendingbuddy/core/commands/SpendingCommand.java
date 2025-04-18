package com.rostyslavliapkin.spendingbuddy.core.commands;

import com.rostyslavliapkin.spendingbuddy.core.Account;
import com.rostyslavliapkin.spendingbuddy.core.Spending;

public class SpendingCommand implements Command {
    private Account account;
    private Spending spending;
    private double amount;



    @Override
    public boolean Execute() {
        return true;
    }

    @Override
    public boolean Undo() {
        return true;
    }
}
