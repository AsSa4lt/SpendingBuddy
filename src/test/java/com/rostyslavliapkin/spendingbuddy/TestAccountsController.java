package com.rostyslavliapkin.spendingbuddy;

import com.rostyslavliapkin.spendingbuddy.controllers.AccountsController;
import com.rostyslavliapkin.spendingbuddy.core.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

public class TestAccountsController {
    private Account accountFirst;
    private Account accountSecond;

    /**
     * Initializes two accounts
     */
    @BeforeEach
    public void SetUp() {
        accountFirst = new Account("Account 1", AccountsController.class.getResource("/images/bankCard.png"));
        accountSecond = new Account("Account 2", AccountsController.class.getResource("/images/bankCard.png"));
        AccountsController.ClearAccounts();
    }

    /**
     * Tests filling AccountController with default entities
     */
    @Test
    public void TestDefaultAccounts(){
        AccountsController.CreateDefaultAccounts();
        assertFalse(AccountsController.GetAccounts().isEmpty());
    }

    /**
     * Test adding new accounts
     */
    @Test
    public void TestAddingAccounts(){
        AccountsController.AddAccount(accountFirst);
        AccountsController.AddAccount(accountSecond);
        assertEquals(2, AccountsController.GetAccounts().size());
    }

    /**
     * Test getting account by name
     */
    @Test
    public void TestGetByName(){
        AccountsController.AddAccount(accountFirst);
        AccountsController.AddAccount(accountSecond);
        assertEquals(accountFirst, AccountsController.GetAccountByName(accountFirst.getName()));
    }

    /**
     * Test removing account
     */
    @Test
    public void TestRemoveAccount(){
        AccountsController.AddAccount(accountFirst);
        AccountsController.AddAccount(accountSecond);
        AccountsController.RemoveAccount(accountFirst);
        assertEquals(1, AccountsController.GetAccounts().size());
    }

    /**
     * Test name availability
     */
    @Test
    public void TestIsNameAvailable(){
        AccountsController.AddAccount(accountFirst);
        assertFalse(AccountsController.IsNameAvailable(accountFirst.getName()));
    }
}
