package com.rostyslavliapkin.spendingbuddy.serializable;

import com.rostyslavliapkin.spendingbuddy.controllers.AccountsController;
import com.rostyslavliapkin.spendingbuddy.core.Account;
import com.rostyslavliapkin.spendingbuddy.core.commands.TransferCommand;

import java.time.YearMonth;

public class TransferCommandSerializable {
    public String SourceAccountName;
    public String TargetAccountName;
    public double Amount;
    public YearMonth CommandYearMonth;

    public TransferCommandSerializable() {}

    public TransferCommandSerializable(TransferCommand command){
        SourceAccountName = command.GetSourceAccount().getName();
        TargetAccountName = command.GetTargetAccount().getName();
        Amount = command.GetAmount();
        CommandYearMonth = command.GetYearMonth();
    }

    public TransferCommand ToTransferCommand(){
        Account sourceAccount = AccountsController.GetAccountByName(SourceAccountName);
        Account targetAccount = AccountsController.GetAccountByName(SourceAccountName);
        return new TransferCommand(sourceAccount, targetAccount, Amount, CommandYearMonth);
    }
}