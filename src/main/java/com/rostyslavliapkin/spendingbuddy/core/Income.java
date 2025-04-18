package com.rostyslavliapkin.spendingbuddy.core;

import java.net.URL;

public class Income extends ResourceEntity {

    public Income(String name, URL imageUrl) {
        super(name, imageUrl);
    }

    @Override
    public EntityType GetType(){
        return EntityType.INCOME;
    }
}
