package com.rostyslavliapkin.spendingbuddy.core;

import java.net.URL;

public class Spending extends ResourceEntity {

    public Spending(String name, URL imageUrl) {
        super(name, imageUrl);
    }

    @Override
    public EntityType GetType(){
        return EntityType.SPENDING;
    }

}
