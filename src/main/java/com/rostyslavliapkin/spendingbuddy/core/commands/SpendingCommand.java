package com.rostyslavliapkin.spendingbuddy.core.commands;

import com.rostyslavliapkin.spendingbuddy.core.Account;
import com.rostyslavliapkin.spendingbuddy.core.Expense;
import com.rostyslavliapkin.spendingbuddy.core.ResourceEntity;

import java.net.URL;
import java.time.YearMonth;

/**
 * Represents a command that spends a specific amount from an account during a specific month.
 */
public class SpendingCommand implements Command {
    /**
     * The account used to spend
     */
    private Account account;

    /**
     * The target expense for command
     */
    private Expense expense;

    /**
     * Amount to be spent
     */
    private double amount;

    /**
     * Year and month when execution took place
     */
    private YearMonth yearMonth;

    /**
     * Constructs a new spending command
     * @param account
     * @param expense
     * @param amount
     * @param yearMonth
     */
    public SpendingCommand(Account account, Expense expense, double amount, YearMonth yearMonth){
        this.account = account;
        this.expense = expense;
        this.amount = amount;
        this.yearMonth = yearMonth;
    }

    /**
     * Executes the spending command
     * @return success of execution
     */
    @Override
    public boolean Execute() {
        return account.Spend(this) && expense.Spend(this);
    }

    /**
     * Undoes the spending command
     * @return success of undoing the command
     */
    @Override
    public boolean Undo() {
        return account.UndoSpend(this) && expense.UndoSpend(this);
    }

    /**
     * @return the image URL associated with this command (from the expense)
     */
    @Override
    public URL GetImageURL(){
        return expense.getImageUrl();
    }


    /**
     * @return account involved in this command
     */
    public Account GetAccount(){
        return account;
    }

    /**
     * @return Expense involved in this command
     */
    public Expense GetExpense(){
        return expense;
    }

    /**
     * @return a description of the spending (e.g., "Card 1 -> Household")
     */
    @Override
    public String GetDescription(){
        return account.getName() + " -> " + expense.getName();
    }

    /**
     * @return the amount transferred
     */
    @Override
    public double GetAmount(){ return amount; }

    /**
     * @return the year and month of this transaction
     */
    @Override
    public YearMonth GetYearMonth() { return yearMonth; }

    /**
     * Checks whether this command involves a specific resource entity.
     *
     * @param entity the entity to check
     * @return true if the command involves the given entity, false otherwise
     */
    @Override
    public boolean InvolvesEntity(ResourceEntity entity) {
        return entity instanceof Account && this.account.equals(entity)
                || entity instanceof Expense && this.expense.equals(entity);
    }
}
