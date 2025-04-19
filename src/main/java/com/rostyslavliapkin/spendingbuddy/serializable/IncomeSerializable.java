package com.rostyslavliapkin.spendingbuddy.serializable;

import com.rostyslavliapkin.spendingbuddy.core.Income;
import java.net.URL;

public class IncomeSerializable {
    public String name;
    public String imageUrl;

    public IncomeSerializable() {}

    public IncomeSerializable(Income income) {
        this.name = income.getName();
        this.imageUrl = income.getImageUrl().toString();
    }

    public Income ToIncome() {
        try {
            return new Income(name, new URL(imageUrl));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}