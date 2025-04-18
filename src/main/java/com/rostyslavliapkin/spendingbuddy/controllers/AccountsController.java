package com.rostyslavliapkin.spendingbuddy.controllers;

import com.rostyslavliapkin.spendingbuddy.core.Account;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class AccountsController {
    private static List<Account> accounts;

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

    public static List<Account> GetAccounts(){
        return accounts;
    }

}
