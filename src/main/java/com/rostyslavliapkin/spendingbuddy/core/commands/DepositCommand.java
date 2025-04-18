package com.rostyslavliapkin.spendingbuddy.core.commands;

import com.rostyslavliapkin.spendingbuddy.core.Account;
import com.rostyslavliapkin.spendingbuddy.core.Income;

public class DepositCommand implements Command {
    private Income income;
    private Account account;
    private double amount;

    public DepositCommand(Income income, Account account, double amount){
        this.income = income;
        this.account = account;
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
