package com.rostyslavliapkin.spendingbuddy.serializable;

import com.rostyslavliapkin.spendingbuddy.controllers.AccountsController;
import com.rostyslavliapkin.spendingbuddy.controllers.IncomesController;
import com.rostyslavliapkin.spendingbuddy.core.Account;
import com.rostyslavliapkin.spendingbuddy.core.Income;
import com.rostyslavliapkin.spendingbuddy.core.commands.DepositCommand;

import java.time.YearMonth;

/**
 * A serializable representation of a {@link DepositCommand}, used for saving or loading app data.
 */
public class DepositCommandSerializable {

    /**
     * The name of the {@link Income} source involved in the command.
     */
    public String IncomeName;

    /**
     * The name of the {@link Account} where the deposit is made.
     */
    public String AccountName;

    /**
     * The amount of money deposited.
     */
    public double Amount;

    /**
     * The year and month when the deposit command was executed.
     */
    public YearMonth CommandYearMonth;

    /**
     * Default constructor required for JSON deserialization.
     */
    public DepositCommandSerializable() {}

    /**
     * Constructs a serializable version of a {@link DepositCommand}.
     *
     * @param command the deposit command to serialize
     */
    public DepositCommandSerializable(DepositCommand command) {
        IncomeName = command.GetIncome().getName();
        AccountName = command.GetAccount().getName();
        Amount = command.GetAmount();
        CommandYearMonth = command.GetYearMonth();
    }

    /**
     * Converts this serializable object back into a {@link DepositCommand} instance.
     * It retrieves actual objects from the {@link IncomesController} and {@link AccountsController}.
     *
     * @return the reconstructed {@link DepositCommand}
     */
    public DepositCommand ToDepositCommand() {
        Income income = IncomesController.GetIncomeByName(IncomeName);
        Account account = AccountsController.GetAccountByName(AccountName);
        return new DepositCommand(income, account, Amount, CommandYearMonth);
    }
}
