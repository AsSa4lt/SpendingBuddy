package com.rostyslavliapkin.spendingbuddy.serializable;

import com.rostyslavliapkin.spendingbuddy.core.Expense;
import java.net.URL;

/**
 * A serializable representation of an {@link Expense}, used for saving and loading data.
 */
public class ExpenseSerializable {

    /**
     * The name of the expense.
     */
    public String name;

    /**
     * A string representation of the image URL associated with the expense.
     */
    public String imageUrl;

    /**
     * Default constructor required for deserialization.
     */
    public ExpenseSerializable() {}

    /**
     * Constructs a serializable version of the given {@link Expense}.
     *
     * @param expense the expense to serialize
     */
    public ExpenseSerializable(Expense expense) {
        this.name = expense.getName();
        this.imageUrl = expense.getImageUrl().toString();
    }

    /**
     * Converts this serializable object back into an {@link Expense} instance.
     *
     * @return a reconstructed {@link Expense} object, or {@code null} if the URL is invalid
     */
    public Expense ToExpense() {
        try {
            return new Expense(name, new URL(imageUrl));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
