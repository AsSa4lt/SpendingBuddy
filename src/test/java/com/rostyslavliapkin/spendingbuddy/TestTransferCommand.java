package com.rostyslavliapkin.spendingbuddy;

import com.rostyslavliapkin.spendingbuddy.controllers.AccountsController;
import com.rostyslavliapkin.spendingbuddy.core.Account;
import com.rostyslavliapkin.spendingbuddy.core.commands.TransferCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.YearMonth;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests for the TransferCommand class, ensuring that money transfer operations
 * between accounts are correctly executed and undone.
 */
public class TestTransferCommand {

    private Account sourceAccount;
    private Account targetAccount;
    private TransferCommand transferCommand;

    /**
     * Initializes the source and target accounts, and the transfer command before each test.
     */
    @BeforeEach
    public void setUp() {
        sourceAccount = new Account("Account 1", AccountsController.class.getResource("/images/bankCard.png"));
        targetAccount = new Account("Account 2", AccountsController.class.getResource("/images/bankCard.png"));
        transferCommand = new TransferCommand(sourceAccount, targetAccount, 500, YearMonth.now());
    }

    /**
     * Tests that a single transfer decreases the source account value by the correct amount
     * and increases the target account value by the same amount.
     */
    @Test
    public void testTransfer() {
        transferCommand.Execute();
        sourceAccount.UpdateFromYearMonth(YearMonth.now());
        targetAccount.UpdateFromYearMonth(YearMonth.now());
        assertEquals(-500, sourceAccount.getValue(), "Source account value should decrease by 500 after transfer.");
        assertEquals(500, targetAccount.getValue(), "Target account value should increase by 500 after transfer.");
    }

    /**
     * Tests that transferring twice correctly adjusts the values in both source and target accounts.
     */
    @Test
    public void testTransferTwice() {
        transferCommand.Execute();
        transferCommand.Execute();
        sourceAccount.UpdateFromYearMonth(YearMonth.now());
        targetAccount.UpdateFromYearMonth(YearMonth.now());
        assertEquals(-1000, sourceAccount.getValue(), "Source account value should decrease by 1000 after two transfers.");
        assertEquals(1000, targetAccount.getValue(), "Target account value should increase by 1000 after two transfers.");
    }

    /**
     * Tests that the transfer operation can be undone, resetting both the source and target account values
     * to their original state.
     */
    @Test
    public void UndoTransfer() {
        transferCommand.Execute();
        transferCommand.Undo();
        sourceAccount.UpdateFromYearMonth(YearMonth.now());
        targetAccount.UpdateFromYearMonth(YearMonth.now());
        assertEquals(0, sourceAccount.getValue(), "Source account value should return to 0 after undoing the transfer.");
        assertEquals(0, targetAccount.getValue(), "Target account value should return to 0 after undoing the transfer.");
    }
}

