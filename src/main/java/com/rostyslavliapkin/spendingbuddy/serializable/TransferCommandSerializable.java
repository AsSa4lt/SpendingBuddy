package com.rostyslavliapkin.spendingbuddy.serializable;

import com.rostyslavliapkin.spendingbuddy.controllers.AccountsController;
import com.rostyslavliapkin.spendingbuddy.core.Account;
import com.rostyslavliapkin.spendingbuddy.core.commands.TransferCommand;

import java.time.YearMonth;

/**
 * A serializable representation of a {@link TransferCommand},
 * used for persisting and reconstructing transfer operations between accounts.
 */
public class TransferCommandSerializable {

    /**
     * The name of the source account (where money is transferred from).
     */
    public String SourceAccountName;

    /**
     * The name of the target account (where money is transferred to).
     */
    public String TargetAccountName;

    /**
     * The amount of money being transferred.
     */
    public double Amount;

    /**
     * The year and month of the transfer operation.
     */
    public YearMonth CommandYearMonth;

    /**
     * Default constructor required for deserialization.
     */
    public TransferCommandSerializable() {}

    /**
     * Constructs a serializable version of the given {@link TransferCommand}.
     *
     * @param command the transfer command to serialize
     */
    public TransferCommandSerializable(TransferCommand command) {
        SourceAccountName = command.GetSourceAccount().getName();
        TargetAccountName = command.GetTargetAccount().getName();
        Amount = command.GetAmount();
        CommandYearMonth = command.GetYearMonth();
    }

    /**
     * Reconstructs the {@link TransferCommand} from the serialized data.
     *
     * @return a new {@link TransferCommand} using the restored data
     */
    public TransferCommand ToTransferCommand() {
        Account sourceAccount = AccountsController.GetAccountByName(SourceAccountName);
        Account targetAccount = AccountsController.GetAccountByName(TargetAccountName);
        return new TransferCommand(sourceAccount, targetAccount, Amount, CommandYearMonth);
    }
}
