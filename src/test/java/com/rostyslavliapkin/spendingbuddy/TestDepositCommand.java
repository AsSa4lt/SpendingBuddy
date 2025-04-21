package com.rostyslavliapkin.spendingbuddy;

import com.rostyslavliapkin.spendingbuddy.controllers.AccountsController;
import com.rostyslavliapkin.spendingbuddy.core.Account;
import com.rostyslavliapkin.spendingbuddy.core.Income;
import com.rostyslavliapkin.spendingbuddy.core.commands.DepositCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.time.YearMonth;

/**
 * Tests for the DepositCommand class, ensuring that deposits are correctly executed and undone on an account and income.
 */
public class TestDepositCommand {

    private Account account;
    private Income income;
    private DepositCommand depositCommand;

    /**
     * Initializes the account, income, and deposit command before each test.
     */
    @BeforeEach
    public void setUp() {
        account = new Account("Account 1", AccountsController.class.getResource("/images/bankCard.png"));
        income = new Income("Income 1", AccountsController.class.getResource("/images/bankCard.png"));
        depositCommand = new DepositCommand(income, account, 500, YearMonth.now());
    }

    /**
     * Tests that a single deposit increases the account and income value by the correct amount.
     */
    @Test
    public void testDeposit() {
        depositCommand.Execute();
        account.UpdateFromYearMonth(YearMonth.now());
        income.UpdateFromYearMonth(YearMonth.now());
        assertEquals(500, account.getValue(), "Account value should be 500 after the deposit.");
        assertEquals(500, income.getValue(), "Income value should be 500 after the deposit.");
    }

    /**
     * Tests that depositing twice correctly doubles the account and income value.
     */
    @Test
    public void testDepositTwice() {
        depositCommand.Execute();
        depositCommand.Execute();
        account.UpdateFromYearMonth(YearMonth.now());
        income.UpdateFromYearMonth(YearMonth.now());
        assertEquals(1000, account.getValue(), "Account value should be 1000 after two deposits.");
        assertEquals(1000, income.getValue(), "Income value should be 1000 after two deposits.");
    }

    /**
     * Tests that the deposit can be undone, resetting both the account and income values to 0.
     */
    @Test
    public void UndoDeposit() {
        depositCommand.Execute();
        depositCommand.Undo();
        account.UpdateFromYearMonth(YearMonth.now());
        income.UpdateFromYearMonth(YearMonth.now());
        assertEquals(0, account.getValue(), "Account value should be 0 after undoing the deposit.");
        assertEquals(0, income.getValue(), "Income value should be 0 after undoing the deposit.");
    }
}

