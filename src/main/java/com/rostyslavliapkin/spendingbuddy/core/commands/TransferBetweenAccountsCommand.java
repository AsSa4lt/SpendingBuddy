package com.rostyslavliapkin.spendingbuddy.core.commands;

import com.rostyslavliapkin.spendingbuddy.core.Account;

import java.time.YearMonth;

public class TransferBetweenAccountsCommand implements Command{
    private Account sourceAccount;
    private Account targetAccount;
    private double amount;
    private YearMonth yearMonth;

    public TransferBetweenAccountsCommand(Account sourceAccount, Account targetAccount, double amount){
        this.sourceAccount = sourceAccount;
        this.targetAccount = targetAccount;
        this.amount = amount;
    }

    @Override
    public boolean Execute() {
        sourceAccount.RemoveAmount(amount, yearMonth);
        targetAccount.AddAmount(amount, yearMonth);
        return true;
    }

    @Override
    public boolean Undo() {
        sourceAccount.AddAmount(amount, yearMonth);
        targetAccount.RemoveAmount(amount, yearMonth);
        return true;
    }
}
