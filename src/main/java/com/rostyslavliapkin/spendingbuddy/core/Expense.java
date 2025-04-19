package com.rostyslavliapkin.spendingbuddy.core;

import com.rostyslavliapkin.spendingbuddy.core.commands.DepositCommand;
import com.rostyslavliapkin.spendingbuddy.core.commands.SpendingCommand;

import java.net.URL;
import java.time.YearMonth;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Expense extends ResourceEntity {
    // We need to store all expenses that happened
    private Map<YearMonth, List<SpendingCommand>> expenses;

    public Expense(String name, URL imageUrl) {
        super(name, imageUrl);
        expenses = new HashMap<>();
    }

    @Override
    public EntityType GetType(){
        return EntityType.EXPENSE;
    }

    /**
     * Updates value of the Account from deposits and expenses that happened this month
     * @param yearMonth which year month to select
     */
    @Override
    public void UpdateFromYearMonth(YearMonth yearMonth) {
        List<SpendingCommand> monthlyExpenses = expenses.getOrDefault(yearMonth, Collections.emptyList());
        double totalExpenses = monthlyExpenses.stream().mapToDouble(SpendingCommand::GetAmount).sum();
        setValue(totalExpenses);
    }
}
