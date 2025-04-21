package com.rostyslavliapkin.spendingbuddy.core;

import com.rostyslavliapkin.spendingbuddy.controllers.AppController;
import com.rostyslavliapkin.spendingbuddy.core.commands.Command;
import com.rostyslavliapkin.spendingbuddy.core.commands.DepositCommand;
import com.rostyslavliapkin.spendingbuddy.core.commands.SpendingCommand;
import com.rostyslavliapkin.spendingbuddy.core.commands.TransferCommand;

import java.net.URL;
import java.time.YearMonth;
import java.util.*;

/**
 * Class that represents expenses of the user
 */
public class Expense extends ResourceEntity {
    /**
     * Map of all expenses related to this expense
     */
    private Map<YearMonth, List<SpendingCommand>> expenses;

    /**
     * Constructs new Expense
     * @param name
     * @param imageUrl
     */
    public Expense(String name, URL imageUrl) {
        super(name, imageUrl);
        expenses = new HashMap<>();
    }

    /**
     * Spends money for this expense
     * @param command to be done
     * @return success of spending the money
     */
    public boolean Spend(SpendingCommand command){
        List<SpendingCommand> list = expenses.computeIfAbsent(command.GetYearMonth(), k -> new ArrayList<>());
        list.add(command);
        UpdateFromYearMonth(AppController.SelectedYearMonth);
        return true;
    }

    /**
     * Removes the command from all lists by undoing it
     * @param command to be undone
     */
    public void RemoveCommand(Command command) {
        if (command instanceof SpendingCommand) {
            UndoSpend((SpendingCommand) command);
        }
    }

    /**
     * Removes all mentions of account from commands
     * @param account to be removed
     */
    public void RemoveAccount(Account account) {
        // Remove any SpendingCommands associated with this account
        for (List<SpendingCommand> spendingList : expenses.values()) {
            spendingList.removeIf(cmd -> cmd.GetAccount().equals(account));
        }
    }

    /**
     * Removes all mentions of expense from commands
     * @param expense to be removed
     */
    public void RemoveExpense(Expense expense) {
        // Remove any SpendingCommands associated with this account
        for (List<SpendingCommand> spendingList : expenses.values()) {
            spendingList.removeIf(cmd -> cmd.GetExpense().equals(expense));
        }
    }

    /**
     * Undoes spending the money from the account
     * @param command to be undone
     * @return success of undoing the command
     */
    public boolean UndoSpend(SpendingCommand command){
        List<SpendingCommand> list = expenses.get(command.GetYearMonth());
        if (list != null) {
            boolean removed = list.remove(command);
            if (list.isEmpty()) {
                expenses.remove(command.GetYearMonth());
            }
            UpdateFromYearMonth(AppController.SelectedYearMonth);
            return removed;
        }
        return false;
    }


    /**
     * Calculates the value of the Account from deposits and expenses that happened this month
     * @param yearMonth which year month to select
     * @return double value of Account
     */
    @Override
    public double GetValueForYearMonth(YearMonth yearMonth){
        List<SpendingCommand> monthlyExpenses = expenses.getOrDefault(yearMonth, Collections.emptyList());
        double totalExpenses = monthlyExpenses.stream().mapToDouble(SpendingCommand::GetAmount).sum();
        return totalExpenses;
    }

    /**
     * @return a type of entity
     */
    @Override
    public EntityType GetType(){
        return EntityType.EXPENSE;
    }
}
