package com.rostyslavliapkin.spendingbuddy;

import com.rostyslavliapkin.spendingbuddy.controllers.AccountsController;
import com.rostyslavliapkin.spendingbuddy.core.Account;
import com.rostyslavliapkin.spendingbuddy.core.Expense;
import com.rostyslavliapkin.spendingbuddy.core.commands.SpendingCommand;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.YearMonth;


/**
 * Tests for the SpendingCommand class, ensuring that spending operations are correctly executed and undone
 * on an account and an expense.
 */
public class TestSpendingCommand {

    private Account account;
    private Expense expense;
    private SpendingCommand spendingCommand;

    /**
     * Initializes the account, expense, and spending command before each test.
     */
    @BeforeEach
    public void setUp() {
        account = new Account("Account 1", AccountsController.class.getResource("/images/bankCard.png"));
        expense = new Expense("Expense 1", AccountsController.class.getResource("/images/bankCard.png"));
        spendingCommand = new SpendingCommand(account, expense, 500, YearMonth.now());
    }

    /**
     * Tests that a single spending decreases the account value by the correct amount and increases the expense value.
     */
    @Test
    public void testSpending() {
        spendingCommand.Execute();
        account.UpdateFromYearMonth(YearMonth.now());
        expense.UpdateFromYearMonth(YearMonth.now());
        assertEquals(-500, account.getValue(), "Account value should decrease by 500 after spending.");
        assertEquals(500, expense.getValue(), "Expense value should be 500 after spending.");
    }

    /**
     * Tests that spending twice correctly doubles the account and expense values.
     */
    @Test
    public void testSpendingTwice() {
        spendingCommand.Execute();
        spendingCommand.Execute();
        account.UpdateFromYearMonth(YearMonth.now());
        expense.UpdateFromYearMonth(YearMonth.now());
        assertEquals(-1000, account.getValue(), "Account value should decrease by 1000 after spending twice.");
        assertEquals(1000, expense.getValue(), "Expense value should be 1000 after spending twice.");
    }

    /**
     * Tests that the spending operation can be undone, resetting both the account and expense values to their original state.
     */
    @Test
    public void UndoSpending() {
        spendingCommand.Execute();
        spendingCommand.Undo();
        account.UpdateFromYearMonth(YearMonth.now());
        expense.UpdateFromYearMonth(YearMonth.now());
        assertEquals(0, account.getValue(), "Account value should return to 0 after undoing the spending.");
        assertEquals(0, expense.getValue(), "Expense value should return to 0 after undoing the spending.");
    }
}

