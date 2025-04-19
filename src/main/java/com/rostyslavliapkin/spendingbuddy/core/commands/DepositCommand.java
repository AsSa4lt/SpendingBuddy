package com.rostyslavliapkin.spendingbuddy.core.commands;

import com.rostyslavliapkin.spendingbuddy.core.Account;
import com.rostyslavliapkin.spendingbuddy.core.Income;

import java.net.URL;
import java.time.YearMonth;

public class DepositCommand implements Command {
    private Income income;
    private Account account;
    private double amount;
    private YearMonth yearMonth;

    public DepositCommand(Income income, Account account, double amount, YearMonth yearMonth){
        this.income = income;
        this.account = account;
        this.amount = amount;
        this.yearMonth = yearMonth;
    }
    @Override
    public boolean Execute() {
        return account.Deposit(this) && income.Deposit(this);
    }

    @Override
    public boolean Undo() {
        return account.UndoDeposit(this) && income.UndoDeposit(this);
    }

    public URL GetImageURL(){
        return income.getImageUrl();
    }

    public String GetDescription(){
        return income.getName() + " -> " + account.getName();
    }

    public double GetAmount(){ return amount; }

    public YearMonth GetYearMonth() { return yearMonth; }
}
