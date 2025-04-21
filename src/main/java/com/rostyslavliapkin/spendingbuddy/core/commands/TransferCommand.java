package com.rostyslavliapkin.spendingbuddy.core.commands;

import com.rostyslavliapkin.spendingbuddy.core.Account;
import com.rostyslavliapkin.spendingbuddy.core.ResourceEntity;

import java.net.URL;
import java.time.YearMonth;

/**
 * Represents a command that spends a specific amount from one account to another
 * during a specific month.
 */
public class TransferCommand implements Command{
    /**
     * Account involved as a source of money
     */
    private Account sourceAccount;

    /**
     * Account involved as a target for a source account
     */
    private Account targetAccount;

    /**
     * Amount to be spent
     */
    private double amount;

    /**
     * Year and month when execution took place
     */
    private YearMonth yearMonth;

    /**
     * Constructs new transfer command
     * @param sourceAccount
     * @param targetAccount
     * @param amount
     * @param yearMonth
     */
    public TransferCommand(Account sourceAccount, Account targetAccount, double amount, YearMonth yearMonth){
        this.sourceAccount = sourceAccount;
        this.targetAccount = targetAccount;
        this.amount = amount;
        this.yearMonth = yearMonth;
    }

    /**
     * Executes the transfer command
     * @return success of execution
     */
    @Override
    public boolean Execute() {
        return sourceAccount.AccountTransfer(this) &&
                targetAccount.AccountTransfer(this);
    }

    /**
     * Undoes the transfer command
     * @return success of undoing the command
     */
    @Override
    public boolean Undo() {
        return sourceAccount.UndoAccountTransfer(this) &&
                targetAccount.UndoAccountTransfer(this);
    }

    /**
     * @return the image URL associated with this command (from the source account)
     */
    @Override
    public URL GetImageURL(){
        return sourceAccount.getImageUrl();
    }

    /**
     * @return target account involved in this command
     */
    public Account GetSourceAccount(){
        return sourceAccount;
    }


    /**
     * @return target account involved in this command
     */
    public Account GetTargetAccount(){
        return targetAccount;
    }

    /**
     * @param account to be checked
     * @return true if this account is source account and false in other cases
     */
    public boolean IsSourceAccount(Account account){
        return account == sourceAccount;
    }


    /**
     * @return a description of the transfer (e.g., "Card 1 -> Cash")
     */
    @Override
    public String GetDescription(){
        return sourceAccount.getName() + " -> " + targetAccount.getName();
    }

    /**
     * @return the amount transferred
     */
    @Override
    public double GetAmount(){ return  amount; }

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
        return entity instanceof Account && this.sourceAccount.equals(entity)
                || entity instanceof Account && this.targetAccount.equals(entity);
    }
}
