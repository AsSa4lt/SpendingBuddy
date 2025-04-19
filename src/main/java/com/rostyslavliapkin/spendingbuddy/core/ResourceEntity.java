package com.rostyslavliapkin.spendingbuddy.core;

import javafx.beans.property.*;

import java.net.URL;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.Map;

public class ResourceEntity {
    private final StringProperty name = new SimpleStringProperty();
    final DoubleProperty value = new SimpleDoubleProperty();
    private final ObjectProperty<URL> imageUrl = new SimpleObjectProperty<>();
    private final Map<YearMonth, Double> monthlyHistory = new HashMap<>();


    public enum EntityType {
        UNDEFINED,
        ACCOUNT,
        INCOME,
        EXPENSE
    }

    public ResourceEntity(String name, URL imageUrl) {
        this.name.set(name);
        this.value.set(0);
        this.imageUrl.set(imageUrl);
    }
    public void addToHistory(double amount, YearMonth month) {
        monthlyHistory.merge(month, amount, Double::sum);
    }

    public void UpdateFromYearMonth(YearMonth yearMonth){
        this.value.set(0);
    }

    public String getName() { return name.get(); }
    public void setName(String name) { this.name.set(name); }
    public StringProperty nameProperty() { return name; }

    public double getValue() { return value.get(); }
    public void setValue(double value) { this.value.set(value); }
    public DoubleProperty valueProperty() { return value; }

    public URL getImageUrl() { return imageUrl.get(); }
    public ObjectProperty<URL> imageUrlProperty() { return imageUrl; }

    public EntityType GetType(){
        return EntityType.UNDEFINED;
    }
}
