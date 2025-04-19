package com.rostyslavliapkin.spendingbuddy.core.commands;

import com.rostyslavliapkin.spendingbuddy.core.Account;
import java.time.YearMonth;

public class TransferBetweenAccountsCommand implements Command{
    private Account sourceAccount;
    private Account targetAccount;
    private double amount;
    private YearMonth yearMonth;

    public TransferBetweenAccountsCommand(Account sourceAccount, Account targetAccount, double amount, YearMonth yearMonth){
        this.sourceAccount = sourceAccount;
        this.targetAccount = targetAccount;
        this.amount = amount;
        this.yearMonth = yearMonth;
    }

    @Override
    public boolean Execute() {
        sourceAccount.AccountTransfer(this);
        targetAccount.AccountTransfer(this);
        return true;
    }

    @Override
    public boolean Undo() {
        sourceAccount.UndoAccountTransfer(this);
        targetAccount.UndoAccountTransfer(this);
        return true;
    }

    public boolean IsSourceAccount(Account account){
        return account == sourceAccount;
    }

    public double GetAmount(){ return  amount; }

    public YearMonth GetYearMonth() { return yearMonth; }
}
