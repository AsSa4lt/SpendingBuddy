package com.rostyslavliapkin.spendingbuddy.serializable;

import com.rostyslavliapkin.spendingbuddy.controllers.AccountsController;
import com.rostyslavliapkin.spendingbuddy.controllers.ExpensesController;
import com.rostyslavliapkin.spendingbuddy.core.Account;
import com.rostyslavliapkin.spendingbuddy.core.Expense;
import com.rostyslavliapkin.spendingbuddy.core.commands.SpendingCommand;

import java.time.YearMonth;

/**
 * A serializable representation of a {@link SpendingCommand},
 * used for saving and loading command history.
 */
public class SpendingCommandSerializable {

    /**
     * The name of the account from which the money was spent.
     */
    public String AccountName;

    /**
     * The name of the expense category or item.
     */
    public String ExpenseName;

    /**
     * The amount of money spent.
     */
    public double Amount;

    /**
     * The year and month when the command was executed.
     */
    public YearMonth CommandYearMonth;

    /**
     * Default constructor required for deserialization.
     */
    public SpendingCommandSerializable() {}

    /**
     * Constructs a serializable version of the given {@link SpendingCommand}.
     *
     * @param command the spending command to serialize
     */
    public SpendingCommandSerializable(SpendingCommand command) {
        AccountName = command.GetAccount().getName();
        ExpenseName = command.GetExpense().getName();
        Amount = command.GetAmount();
        CommandYearMonth = command.GetYearMonth();
    }

    /**
     * Reconstructs the {@link SpendingCommand} from the serialized data.
     *
     * @return a new {@link SpendingCommand} using the restored data
     */
    public SpendingCommand ToSpendingCommand() {
        Account account = AccountsController.GetAccountByName(AccountName);
        Expense expense = ExpensesController.GetExpenseByName(ExpenseName);
        return new SpendingCommand(account, expense, Amount, CommandYearMonth);
    }
}
