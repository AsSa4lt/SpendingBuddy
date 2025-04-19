package com.rostyslavliapkin.spendingbuddy.core;

import com.rostyslavliapkin.spendingbuddy.core.commands.DepositCommand;
import com.rostyslavliapkin.spendingbuddy.core.commands.SpendingCommand;

import java.net.URL;
import java.time.YearMonth;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Income extends ResourceEntity {
    // We need only to store the deposits that happened
    private Map<YearMonth, List<DepositCommand>> deposits;

    public Income(String name, URL imageUrl) {
        super(name, imageUrl);
        deposits = new HashMap<>();
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

    @Override
    public EntityType GetType(){
        return EntityType.INCOME;
    }
}
