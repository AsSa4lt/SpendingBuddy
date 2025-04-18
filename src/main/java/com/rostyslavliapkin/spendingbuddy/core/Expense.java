package com.rostyslavliapkin.spendingbuddy.core;

import java.net.URL;

public class Expense extends ResourceEntity {

    public Expense(String name, URL imageUrl) {
        super(name, imageUrl);
    }

    @Override
    public EntityType GetType(){
        return EntityType.SPENDING;
    }

}
