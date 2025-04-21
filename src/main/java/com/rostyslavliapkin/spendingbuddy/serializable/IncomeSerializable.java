package com.rostyslavliapkin.spendingbuddy.serializable;

import com.rostyslavliapkin.spendingbuddy.core.Income;
import java.net.URL;

/**
 * A serializable representation of an {@link Income}, used for saving and loading data.
 */
public class IncomeSerializable {

    /**
     * The name of the income source.
     */
    public String name;

    /**
     * A string representation of the image URL associated with the income.
     */
    public String imageUrl;

    /**
     * Default constructor required for deserialization.
     */
    public IncomeSerializable() {}

    /**
     * Constructs a serializable version of the given {@link Income}.
     *
     * @param income the income to serialize
     */
    public IncomeSerializable(Income income) {
        this.name = income.getName();
        this.imageUrl = income.getImageUrl().toString();
    }

    /**
     * Converts this serializable object back into an {@link Income} instance.
     *
     * @return a reconstructed {@link Income} object, or {@code null} if the URL is invalid
     */
    public Income ToIncome() {
        try {
            return new Income(name, new URL(imageUrl));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
