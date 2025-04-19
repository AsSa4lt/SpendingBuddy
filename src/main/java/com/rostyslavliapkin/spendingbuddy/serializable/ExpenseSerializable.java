package com.rostyslavliapkin.spendingbuddy.serializable;

import com.rostyslavliapkin.spendingbuddy.core.Expense;
import java.net.URL;

public class ExpenseSerializable {
    public String name;
    public String imageUrl;

    public ExpenseSerializable() {}

    public ExpenseSerializable(Expense expense) {
        this.name = expense.getName();
        this.imageUrl = expense.getImageUrl().toString();
    }

    public Expense ToExpense() {
        try {
            return new Expense(name, new URL(imageUrl));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
