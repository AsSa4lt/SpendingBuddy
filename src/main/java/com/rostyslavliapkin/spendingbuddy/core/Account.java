package com.rostyslavliapkin.spendingbuddy.core;

import com.rostyslavliapkin.spendingbuddy.controllers.AppController;
import com.rostyslavliapkin.spendingbuddy.core.commands.DepositCommand;
import com.rostyslavliapkin.spendingbuddy.core.commands.SpendingCommand;
import com.rostyslavliapkin.spendingbuddy.core.commands.TransferBetweenAccountsCommand;

import java.net.URL;
import java.time.YearMonth;
import java.util.*;

public class Account extends ResourceEntity {
    // We need to store all the deposits that happened
    private Map<YearMonth, List<DepositCommand>> deposits;
    // We need to store all expenses that happened
    private Map<YearMonth, List<SpendingCommand>> expenses;
    // We need to store all movements between accounts
    private Map<YearMonth, List<TransferBetweenAccountsCommand>> transfers;

    public Account(String name, URL imageUrl) {
        super(name, imageUrl);
        deposits = new HashMap<>();
        expenses = new HashMap<>();
        transfers = new HashMap<>();
    }

    public boolean AccountTransfer(TransferBetweenAccountsCommand command) {
        List<TransferBetweenAccountsCommand> list = transfers.computeIfAbsent(command.GetYearMonth(), k -> new ArrayList<>());
        list.add(command);
        UpdateFromYearMonth(AppController.SelectedYearMonth);
        return true;
    }

    public boolean UndoAccountTransfer(TransferBetweenAccountsCommand command) {
        List<TransferBetweenAccountsCommand> list = transfers.get(command.GetYearMonth());
        if (list != null) {
            boolean removed = list.remove(command);
            if (list.isEmpty()) {
                transfers.remove(command.GetYearMonth());
            }
            UpdateFromYearMonth(AppController.SelectedYearMonth);
            return removed;
        }
        return false;
    }



    private boolean removeTransferCommand(YearMonth yearMonth, TransferBetweenAccountsCommand command) {
        List<TransferBetweenAccountsCommand> list = transfers.get(yearMonth);
        if (list != null) {
            boolean removed = list.remove(command);
            if (list.isEmpty()) {
                transfers.remove(yearMonth); // Clean up empty list
            }
            return removed;
        }
        return false;
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
        List<TransferBetweenAccountsCommand> monthlyTransfers = transfers.getOrDefault(yearMonth, Collections.emptyList());

        double totalDeposits = monthlyDeposits.stream().mapToDouble(DepositCommand::GetAmount).sum();
        double totalExpenses = monthlyExpenses.stream().mapToDouble(SpendingCommand::GetAmount).sum();

        double totalTransfers = 0;
        for (TransferBetweenAccountsCommand command : monthlyTransfers){
            if(command.IsSourceAccount(this))
                totalTransfers -= command.GetAmount();
            else
                totalTransfers += command.GetAmount();
        }

        double newValue = totalDeposits - totalExpenses + totalTransfers;
        setValue(newValue);
    }

    @Override
    public EntityType GetType(){
        return EntityType.ACCOUNT;
    }
}
