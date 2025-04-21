package com.rostyslavliapkin.spendingbuddy.core;

import javafx.beans.property.*;
import jdk.jshell.spi.ExecutionControl;

import java.net.URL;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.Map;

/**
 * Class the represents entities of the user
 */
public class ResourceEntity {
    /**
     * Name property of the entity
     */
    private final StringProperty name = new SimpleStringProperty();

    /**
     * Value property of the entity
     */
    final DoubleProperty value = new SimpleDoubleProperty();

    /**
     * Image url property of the enitity
     */
    private final ObjectProperty<URL> imageUrl = new SimpleObjectProperty<>();


    /**
     * Defines an enum of all types of entities
     */
    public enum EntityType {
        UNDEFINED,
        ACCOUNT,
        INCOME,
        EXPENSE
    }

    /**
     * Constructs a new resource entity
     * @param name
     * @param imageUrl
     */
    public ResourceEntity(String name, URL imageUrl) {
        this.name.set(name);
        this.value.set(0);
        this.imageUrl.set(imageUrl);
    }

    /**
     * @param yearMonth to get values
     * @return value of the resource for a selected month
     */
    public double GetValueForYearMonth(YearMonth yearMonth){
        return 0;
    }

    /**
     * Updates value of the ResourceEntity
     * @param yearMonth which year month to select
     */
    public void UpdateFromYearMonth(YearMonth yearMonth){
        this.value.set(GetValueForYearMonth(yearMonth));
    }

    /**
     * @return a name of the entity
     */
    public String getName() { return name.get(); }

    /**
     * Sets a name for the entity
     * @param name to set
     */
    public void setName(String name) { this.name.set(name); }

    /**
     * @return a string property of entity
     */
    public StringProperty nameProperty() { return name; }

    /**
     * @return a value of this entity
     */
    public double getValue() { return value.get(); }

    /**
     * Sets value for entity
     * @param value to set
     */
    public void setValue(double value) { this.value.set(value); }

    /**
     * @return double property of the entity
     */
    public DoubleProperty valueProperty() { return value; }

    /**
     * @return image url for the entity
     */
    public URL getImageUrl() { return imageUrl.get(); }

    /**
     * @return image url property for entity
     */
    public ObjectProperty<URL> imageUrlProperty() { return imageUrl; }

    /**
     * @return a type of the entity
     */
    public EntityType GetType(){
        return EntityType.UNDEFINED;
    }
}
