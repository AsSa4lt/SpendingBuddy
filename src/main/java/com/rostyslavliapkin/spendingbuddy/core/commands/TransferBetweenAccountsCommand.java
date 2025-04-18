package com.rostyslavliapkin.spendingbuddy.core.commands;

import com.rostyslavliapkin.spendingbuddy.core.Account;

public class TransferBetweenAccountsCommand implements Command{
    private Account sourceAccount;
    private Account targetAccount;
    private double amount;

    public TransferBetweenAccountsCommand(Account sourceAccount, Account targetAccount, double amount){
        this.sourceAccount = sourceAccount;
        this.targetAccount = targetAccount;
        this.amount = amount;
    }

    @Override
    public boolean Execute() {
        sourceAccount.RemoveAmount(amount);
        targetAccount.AddAmount(amount);
        return true;
    }

    @Override
    public boolean Undo() {
        sourceAccount.AddAmount(amount);
        targetAccount.RemoveAmount(amount);
        return true;
    }
}
