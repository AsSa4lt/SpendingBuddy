package com.rostyslavliapkin.spendingbuddy.core.commands;

import com.rostyslavliapkin.spendingbuddy.core.Account;
import com.rostyslavliapkin.spendingbuddy.core.Expense;
import com.rostyslavliapkin.spendingbuddy.core.ResourceEntity;

import java.net.URL;
import java.time.YearMonth;

public class TransferCommand implements Command{
    private Account sourceAccount;
    private Account targetAccount;
    private double amount;
    private YearMonth yearMonth;

    public TransferCommand(Account sourceAccount, Account targetAccount, double amount, YearMonth yearMonth){
        this.sourceAccount = sourceAccount;
        this.targetAccount = targetAccount;
        this.amount = amount;
        this.yearMonth = yearMonth;
    }

    @Override
    public boolean Execute() {
        return sourceAccount.AccountTransfer(this) &&
                targetAccount.AccountTransfer(this);
    }

    @Override
    public boolean Undo() {
        return sourceAccount.UndoAccountTransfer(this) &&
                targetAccount.UndoAccountTransfer(this);
    }

    public Account GetSourceAccount(){
        return sourceAccount;
    }

    public Account GetTargetAccount(){
        return targetAccount;
    }

    public boolean IsSourceAccount(Account account){
        return account == sourceAccount;
    }

    @Override
    public URL GetImageURL(){
        return sourceAccount.getImageUrl();
    }

    @Override
    public String GetDescription(){
        return sourceAccount.getName() + " -> " + targetAccount.getName();
    }

    @Override
    public double GetAmount(){ return  amount; }

    @Override
    public YearMonth GetYearMonth() { return yearMonth; }

    @Override
    public boolean InvolvesEntity(ResourceEntity entity) {
        return entity instanceof Account && this.sourceAccount.equals(entity)
                || entity instanceof Account && this.targetAccount.equals(entity);
    }
}
