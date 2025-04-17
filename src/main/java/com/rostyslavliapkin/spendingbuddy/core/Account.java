package com.rostyslavliapkin.spendingbuddy.core;

import java.net.URL;

public class Account {
    private String name;
    private URL imageUrl; // Universal, OS-independent resource reference
    private double value;

    public Account(String name, URL imageUrl) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.value = 0;
    }

    public String GetName() {
        return name;
    }

    public URL GetImageUrl() {
        return imageUrl;
    }

    public double GetValue(){
        return value;
    }
}
