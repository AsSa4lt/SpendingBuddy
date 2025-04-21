package com.rostyslavliapkin.spendingbuddy.core;

import com.rostyslavliapkin.spendingbuddy.controllers.AppController;
import com.rostyslavliapkin.spendingbuddy.core.commands.DepositCommand;
import com.rostyslavliapkin.spendingbuddy.core.commands.SpendingCommand;

import java.net.URL;
import java.time.YearMonth;
import java.util.*;

public class Expense extends ResourceEntity {
    // We need to store all expenses that happened
    private Map<YearMonth, List<SpendingCommand>> expenses;

    public Expense(String name, URL imageUrl) {
        super(name, imageUrl);
        expenses = new HashMap<>();
    }

    public boolean Spend(SpendingCommand command){
        List<SpendingCommand> list = expenses.computeIfAbsent(command.GetYearMonth(), k -> new ArrayList<>());
        list.add(command);
        UpdateFromYearMonth(AppController.SelectedYearMonth);
        return true;
    }

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

    @Override
    public EntityType GetType(){
        return EntityType.EXPENSE;
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


}
