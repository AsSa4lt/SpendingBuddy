package com.rostyslavliapkin.spendingbuddy.controllers;

import com.rostyslavliapkin.spendingbuddy.core.Account;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class AccountsController {
    private static List<Account> accounts = new ArrayList<>();

    public static void CreateDefaultAccounts() {
        //Account account1 = new Account();
        accounts = new ArrayList<>();
        URL imageUrl = AccountsController.class.getResource("/images/bankCard.png");
        URL imageUrl2 = AccountsController.class.getResource("/images/cash.png");

        Account account = new Account("Card1", imageUrl);
        Account account2 = new Account("Cash", imageUrl2);
        accounts.add(account);
        accounts.add(account2);
    }

    public static void ClearAccounts(){
        accounts.clear();
    }

    public static Account GetAccountByName(String name){
        for(Account account : accounts){
            if (account.getName().equals(name))
                return account;
        }
        return null;
    }

    public static void AddAccount(Account account){
        accounts.add(account);
    }

    public static List<Account> GetAccounts(){
        return accounts;
    }

}
