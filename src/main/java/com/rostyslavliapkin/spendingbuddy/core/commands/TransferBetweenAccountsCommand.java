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
        System.out.println("Values are: " + sourceAccount.getValue() + ", " + targetAccount.getValue());
        sourceAccount.RemoveAmount(amount);
        targetAccount.AddAmount(amount);
        System.out.println("Values are: " + sourceAccount.getValue() + ", " + targetAccount.getValue());
        return true;
    }

    @Override
    public boolean Undo() {
        return true;
    }
}
