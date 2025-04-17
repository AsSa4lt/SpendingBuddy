package com.rostyslavliapkin.spendingbuddy.controllers;

import com.rostyslavliapkin.spendingbuddy.core.Account;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class AccountsController {
    private static List<Account> accounts;
    static {
        //Account account1 = new Account();
        accounts = new ArrayList<>();
        URL imageUrl = AccountsController.class.getResource("/images/bankCard.png");
        Account account = new Account("My Account 1", imageUrl);
        Account account2 = new Account("My Account 2", imageUrl);
        accounts.add(account);
        accounts.add(account);
        //Account account2 = new Account();
    }

    public static List<Account> GetAccounts(){
        return accounts;
    }

}
