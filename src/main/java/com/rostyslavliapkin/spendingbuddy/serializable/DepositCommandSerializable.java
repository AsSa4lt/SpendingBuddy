package com.rostyslavliapkin.spendingbuddy.serializable;

import com.rostyslavliapkin.spendingbuddy.controllers.AccountsController;
import com.rostyslavliapkin.spendingbuddy.controllers.IncomesController;
import com.rostyslavliapkin.spendingbuddy.core.Account;
import com.rostyslavliapkin.spendingbuddy.core.Income;
import com.rostyslavliapkin.spendingbuddy.core.commands.DepositCommand;

import java.time.YearMonth;

public class DepositCommandSerializable {
    public String IncomeName;
    public String AccountName;
    public double Amount;
    public YearMonth CommandYearMonth;

    public DepositCommandSerializable() {}

    public DepositCommandSerializable(DepositCommand command){
        IncomeName = command.GetIncome().getName();
        AccountName = command.GetAccount().getName();
        Amount = command.GetAmount();
        CommandYearMonth = command.GetYearMonth();
    }

    public DepositCommand ToDepositCommand(){
        Income income = IncomesController.GetIncomeByName(IncomeName);
        Account account = AccountsController.GetAccountByName(AccountName);
        return new DepositCommand(income, account, Amount, CommandYearMonth);
    }
}
