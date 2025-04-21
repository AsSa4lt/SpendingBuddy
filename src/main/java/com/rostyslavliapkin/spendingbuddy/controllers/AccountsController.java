package com.rostyslavliapkin.spendingbuddy.controllers;

import com.rostyslavliapkin.spendingbuddy.core.Account;
import com.rostyslavliapkin.spendingbuddy.core.Expense;
import com.rostyslavliapkin.spendingbuddy.core.Income;
import com.rostyslavliapkin.spendingbuddy.core.commands.Command;

import java.util.ArrayList;
import java.util.List;

public class AccountsController {
    private static List<Account> accounts = new ArrayList<>();

    public static void CreateDefaultAccounts() {
        accounts = new ArrayList<>();
        accounts.add(new Account("Card 1", AccountsController.class.getResource("/images/bankCard.png")));
        accounts.add(new Account("Card 2", AccountsController.class.getResource("/images/bankCard.png")));
        accounts.add(new Account("Cash", AccountsController.class.getResource("/images/cash.png")));
    }

    public static void ClearAccounts(){
        accounts.clear();
    }

    public static void RemoveCommand(Command command){
        for(Account account : accounts){
            account.RemoveCommand(command);
        }
    }

    public static Account GetAccountByName(String name){
        for(Account account : accounts){
            if (account.getName().equals(name))
                return account;
        }
        return null;
    }

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

    public static void AddAccount(Account account){
        accounts.add(account);
    }

    public static List<Account> GetAccounts(){
        return accounts;
    }

}
