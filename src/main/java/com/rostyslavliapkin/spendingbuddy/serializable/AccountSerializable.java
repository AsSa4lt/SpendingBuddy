package com.rostyslavliapkin.spendingbuddy.serializable;

import com.rostyslavliapkin.spendingbuddy.core.Account;
import java.net.URL;

public class AccountSerializable {
    public String name;
    public String imageUrl;

    public AccountSerializable() {}

    public AccountSerializable(Account account) {
        this.name = account.getName();
        this.imageUrl = account.getImageUrl().toString();
    }

    public Account ToAccount() {
        try {
            return new Account(name, new URL(imageUrl));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

