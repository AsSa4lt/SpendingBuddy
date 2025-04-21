package com.rostyslavliapkin.spendingbuddy.core;

import com.rostyslavliapkin.spendingbuddy.controllers.AppController;
import com.rostyslavliapkin.spendingbuddy.core.commands.Command;
import com.rostyslavliapkin.spendingbuddy.core.commands.DepositCommand;

import java.net.URL;
import java.time.YearMonth;
import java.util.*;

/**
 * Class that represents income of the user
 */
public class Income extends ResourceEntity {
    /**
     * Map of all deposits from this income
     */
    private Map<YearMonth, List<DepositCommand>> deposits;

    /**
     * Constructs a new income
     * @param name
     * @param imageUrl
     */
    public Income(String name, URL imageUrl) {
        super(name, imageUrl);
        deposits = new HashMap<>();
    }

    /**
     * Deposits money from this income to account
     * @param command to deposit money
     * @return success of depositing the money
     */
    public boolean Deposit(DepositCommand command){
        List<DepositCommand> list = deposits.computeIfAbsent(command.GetYearMonth(), k -> new ArrayList<>());
        list.add(command);
        UpdateFromYearMonth(AppController.SelectedYearMonth);
        return true;
    }

    /**
     * Removes command by undoing it
     * @param command
     */
    public void RemoveCommand(Command command) {
        if (command instanceof DepositCommand) {
            UndoDeposit((DepositCommand) command);
        }
    }


    /**
     * Removes all mentions of the income from lists
     * @param income to be removed
     */
    public void RemoveIncome(Income income) {
        // Remove any SpendingCommands associated with this account
        for (List<DepositCommand> depositList : deposits.values()) {
            depositList.removeIf(cmd -> cmd.GetIncome().equals(income));
        }
    }


    /**
     * Removes all mentions of the account from this income
     * @param account
     */
    public void RemoveAccount(Account account) {
        // Remove any SpendingCommands associated with this account
        for (List<DepositCommand> depositList : deposits.values()) {
            depositList.removeIf(cmd -> cmd.GetAccount().equals(account));
        }
    }

    /**
     * Undoes the deposit from this income
     * @param command deposit to be undone
     * @return success of undoing deposit
     */
    public boolean UndoDeposit(DepositCommand command){
        List<DepositCommand> list = deposits.get(command.GetYearMonth());
        if (list != null) {
            boolean removed = list.remove(command);
            if (list.isEmpty()) {
                deposits.remove(command.GetYearMonth());
            }
            UpdateFromYearMonth(AppController.SelectedYearMonth);
            return removed;
        }
        return false;
    }

    /**
     * Updates value of the Income from deposits that happened this month
     * @param yearMonth which year month to select
     */
    @Override
    public void UpdateFromYearMonth(YearMonth yearMonth) {
        List<DepositCommand> monthlyDeposits = deposits.getOrDefault(yearMonth, Collections.emptyList());
        double totalDeposits = monthlyDeposits.stream().mapToDouble(DepositCommand::GetAmount).sum();
        double newValue = totalDeposits;
        setValue(newValue);
    }

    /**
     * @return a type of entity
     */
    @Override
    public EntityType GetType(){
        return EntityType.INCOME;
    }
}
