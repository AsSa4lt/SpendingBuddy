package com.rostyslavliapkin.spendingbuddy.controllers;

import com.rostyslavliapkin.spendingbuddy.core.Account;
import com.rostyslavliapkin.spendingbuddy.core.Expense;
import com.rostyslavliapkin.spendingbuddy.core.Income;
import com.rostyslavliapkin.spendingbuddy.core.commands.Command;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that controls all accounts and provides some basic functions
 */
public class AccountsController {
    /**
     * List of all accounts of the user
     */
    private static List<Account> accounts = new ArrayList<>();

    /**
     * Function to fill some default accounts is there is no user data
     */
    public static void CreateDefaultAccounts() {
        accounts = new ArrayList<>();
        accounts.add(new Account("Card 1", AccountsController.class.getResource("/images/bankCard.png")));
        accounts.add(new Account("Card 2", AccountsController.class.getResource("/images/bankCard.png")));
        accounts.add(new Account("Cash", AccountsController.class.getResource("/images/cash.png")));
    }

    /**
     * Function to clear all accounts
     */
    public static void ClearAccounts(){
        accounts.clear();
    }

    /**
     * Function to remove command from all accounts
     * @param command that we are going to remove
     */
    public static void RemoveCommand(Command command){
        for(Account account : accounts){
            account.RemoveCommand(command);
        }
    }

    /**
     *
     * @param name the name to look after
     * @return Account that we found, or null in case there is no account
     */
    public static Account GetAccountByName(String name){
        for(Account account : accounts){
            if (account.getName().equals(name))
                return account;
        }
        return null;
    }

    /**
     * @param name to check
     * @return checks and returns if name is available
     */
    public static boolean IsNameAvailable(String name){
        for (Account account: accounts){
            if (account.getName().equals(name))
                return false;
        }
        return true;
    }

    /**
     * Function that removes all signs of existence for account
     * @param removedAccount Account that we need to remove from everywhere
     */
    public static void RemoveAccount(Account removedAccount){
        accounts.remove(removedAccount);
        for(Account account : accounts){
            account.RemoveAccount(removedAccount);
        }
        for(Income income : IncomesController.GetIncomes()){
            income.RemoveAccount(removedAccount);
        }
        for(Expense expense : ExpensesController.GetExpenses()){
            expense.RemoveAccount(removedAccount);
        }
    }

    /**
     * Function to add account to the list
     * @param account To be added
     */
    public static void AddAccount(Account account){
        accounts.add(account);
    }

    /**
     * Function to add account to the list of accounts
     * @return A list of all accounts
     */
    public static List<Account> GetAccounts(){
        return accounts;
    }

}
