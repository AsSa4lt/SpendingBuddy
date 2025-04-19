package com.rostyslavliapkin.spendingbuddy.core;

import com.rostyslavliapkin.spendingbuddy.core.commands.DepositCommand;
import com.rostyslavliapkin.spendingbuddy.core.commands.SpendingCommand;

import java.net.URL;
import java.time.YearMonth;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Account extends ResourceEntity {
    // We need to store all the deposits that happened
    private Map<YearMonth, List<DepositCommand>> deposits;
    // We need to store all expenses that happened
    private Map<YearMonth, List<SpendingCommand>> expenses;
    //private final Map<>

    public Account(String name, URL imageUrl) {
        super(name, imageUrl);
        deposits = new HashMap<>();
        expenses = new HashMap<>();
    }

    public boolean AddAmount(double amount, YearMonth month){
        setValue(getValue() + amount);
        addToHistory(amount, month);
        return true;
    }

    public boolean RemoveAmount(double amount, YearMonth month){
        setValue(getValue() - amount);
        addToHistory(-amount, month);
        return true;
    }

    /**
     * Updates value of the Account from deposits and expenses that happened this month
     * @param yearMonth which year month to select
     */
    @Override
    public void UpdateFromYearMonth(YearMonth yearMonth) {
        List<DepositCommand> monthlyDeposits = deposits.getOrDefault(yearMonth, Collections.emptyList());
        List<SpendingCommand> monthlyExpenses = expenses.getOrDefault(yearMonth, Collections.emptyList());

        double totalDeposits = monthlyDeposits.stream().mapToDouble(DepositCommand::GetAmount).sum();
        double totalExpenses = monthlyExpenses.stream().mapToDouble(SpendingCommand::GetAmount).sum();

        double newValue = totalDeposits - totalExpenses;
        setValue(newValue);
    }

    @Override
    public EntityType GetType(){
        return EntityType.ACCOUNT;
    }
}
