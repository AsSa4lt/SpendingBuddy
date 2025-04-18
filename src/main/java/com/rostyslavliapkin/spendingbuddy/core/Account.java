package com.rostyslavliapkin.spendingbuddy.core;

import java.net.URL;

public class Account extends ResourceEntity {

    public Account(String name, URL imageUrl) {
        super(name, imageUrl);
    }

    public boolean RemoveAmount(double amount){
        setValue(getValue()-amount);
        return true;
    }

    public boolean AddAmount(double amount){
        setValue(getValue()+amount);
        return true;
    }

    @Override
    public EntityType GetType(){
        return EntityType.ACCOUNT;
    }
}
