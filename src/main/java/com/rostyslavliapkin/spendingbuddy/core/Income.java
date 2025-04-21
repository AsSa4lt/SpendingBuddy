package com.rostyslavliapkin.spendingbuddy.core;

import com.rostyslavliapkin.spendingbuddy.controllers.AppController;
import com.rostyslavliapkin.spendingbuddy.core.commands.Command;
import com.rostyslavliapkin.spendingbuddy.core.commands.DepositCommand;
import com.rostyslavliapkin.spendingbuddy.core.commands.SpendingCommand;

import java.net.URL;
import java.time.YearMonth;
import java.util.*;

public class Income extends ResourceEntity {
    // We need only to store the deposits that happened
    private Map<YearMonth, List<DepositCommand>> deposits;

    public Income(String name, URL imageUrl) {
        super(name, imageUrl);
        deposits = new HashMap<>();
    }

    public boolean Deposit(DepositCommand command){
        List<DepositCommand> list = deposits.computeIfAbsent(command.GetYearMonth(), k -> new ArrayList<>());
        list.add(command);
        UpdateFromYearMonth(AppController.SelectedYearMonth);
        return true;
    }

    public void RemoveCommand(Command command) {
        if (command instanceof DepositCommand) {
            UndoDeposit((DepositCommand) command);
        }
    }


    public void RemoveIncome(Income income) {
        // Remove any SpendingCommands associated with this account
        for (List<DepositCommand> depositList : deposits.values()) {
            depositList.removeIf(cmd -> cmd.GetIncome().equals(income));
        }
    }


    public void RemoveAccount(Account account) {
        // Remove any SpendingCommands associated with this account
        for (List<DepositCommand> depositList : deposits.values()) {
            depositList.removeIf(cmd -> cmd.GetAccount().equals(account));
        }
    }

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

    @Override
    public EntityType GetType(){
        return EntityType.INCOME;
    }
}
