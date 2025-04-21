package com.rostyslavliapkin.spendingbuddy.serializable;

import com.rostyslavliapkin.spendingbuddy.core.Account;
import java.net.URL;

/**
 * A serializable representation of an {@link Account}, used for JSON storage or transfer.
 * This class provides a lightweight version of Account with only essential fields.
 */
public class AccountSerializable {

    /**
     * The name of the account.
     */
    public String name;

    /**
     * A string representation of the account's image URL.
     */
    public String imageUrl;

    /**
     * Default no-args constructor required for serialization/deserialization.
     */
    public AccountSerializable() {}

    /**
     * Constructs a serializable version of an {@link Account}.
     *
     * @param account the account to convert into a serializable form
     */
    public AccountSerializable(Account account) {
        this.name = account.getName();
        this.imageUrl = account.getImageUrl().toString();
    }

    /**
     * Converts this serializable object back into an {@link Account}.
     *
     * @return the reconstructed Account object, or {@code null} if the image URL is malformed
     */
    public Account ToAccount() {
        try {
            return new Account(name, new URL(imageUrl));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}


