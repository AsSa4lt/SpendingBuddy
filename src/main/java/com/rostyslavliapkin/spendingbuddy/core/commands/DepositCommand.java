package com.rostyslavliapkin.spendingbuddy.core.commands;

import com.rostyslavliapkin.spendingbuddy.core.Account;
import com.rostyslavliapkin.spendingbuddy.core.Income;
import com.rostyslavliapkin.spendingbuddy.core.ResourceEntity;

import java.net.URL;
import java.time.YearMonth;

/**
 * Represents a command that deposits a specific amount from an income source to an account
 * during a specific month.
 */
public class DepositCommand implements Command {
    /**
     * The income source used for the deposit.
     */
    private Income income;

    /**
     * The target account receiving the deposit.
     */
    private Account account;

    /**
     * The amount to be deposited.
     */
    private double amount;

    /**
     * The year and month when this transaction took place.
     */
    private YearMonth yearMonth;

    /**
     * Constructs a new deposit command
     * @param income
     * @param account
     * @param amount
     * @param yearMonth
     */
    public DepositCommand(Income income, Account account, double amount, YearMonth yearMonth){
        this.income = income;
        this.account = account;
        this.amount = amount;
        this.yearMonth = yearMonth;
    }

    /**
     * Executes the deposit function
     * @return success of execution
     */
    @Override
    public boolean Execute() {
        return account.Deposit(this) && income.Deposit(this);
    }

    /**
     * Undoes the deposit command
     * @return
     */
    @Override
    public boolean Undo() {
        return account.UndoDeposit(this) && income.UndoDeposit(this);
    }

    /**
     * @return the image URL associated with this command (from the income source)
     */
    @Override
    public URL GetImageURL(){
        return income.getImageUrl();
    }

    /**
     * @return the income involved in this command
     */
    public Income GetIncome(){
        return income;
    }

    /**
     * @return the account receiving the deposit
     */
    public Account GetAccount(){
        return account;
    }

    /**
     * @return a description of the deposit (e.g., "Salary -> Main Account")
     */
    @Override
    public String GetDescription(){
        return income.getName() + " -> " + account.getName();
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
                || entity instanceof Income && this.income.equals(entity);
    }
}
