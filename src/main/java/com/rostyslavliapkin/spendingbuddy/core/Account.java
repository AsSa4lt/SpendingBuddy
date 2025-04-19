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
    private final Map<YearMonth, List<DepositCommand>> deposits;
    // We need to store all expenses that happened
    private final Map<YearMonth, List<SpendingCommand>> expenses;
    // We need to store all movements between accounts
    private final Map<YearMonth, List<TransferBetweenAccountsCommand>> transfers;

    public Account(String name, URL imageUrl) {
        super(name, imageUrl);
        deposits = new HashMap<>();
        expenses = new HashMap<>();
        transfers = new HashMap<>();
    }

    public boolean Deposit(DepositCommand command){
        List<DepositCommand> list = deposits.computeIfAbsent(command.GetYearMonth(), _ -> new ArrayList<>());
        list.add(command);
        UpdateFromYearMonth(AppController.SelectedYearMonth);
        return true;
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

    public boolean Spend(SpendingCommand command){
        List<SpendingCommand> list = expenses.computeIfAbsent(command.GetYearMonth(), _ -> new ArrayList<>());
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

    public boolean AccountTransfer(TransferBetweenAccountsCommand command) {
        List<TransferBetweenAccountsCommand> list = transfers.computeIfAbsent(command.GetYearMonth(), _ -> new ArrayList<>());
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



    /**
     * Updates value of the Account from deposits and expenses that happened this month
     * @param selectedMonth which year month to select
     */
    @Override
    public void UpdateFromYearMonth(YearMonth selectedMonth) {
        double totalDeposits = 0;
        double totalExpenses = 0;
        double totalTransfers = 0;

        for (Map.Entry<YearMonth, List<DepositCommand>> entry : deposits.entrySet()) {
            if (!entry.getKey().isAfter(selectedMonth)) {
                totalDeposits += entry.getValue().stream().mapToDouble(DepositCommand::GetAmount).sum();
            }
        }

        for (Map.Entry<YearMonth, List<SpendingCommand>> entry : expenses.entrySet()) {
            if (!entry.getKey().isAfter(selectedMonth)) {
                totalExpenses += entry.getValue().stream().mapToDouble(SpendingCommand::GetAmount).sum();
            }
        }

        for (Map.Entry<YearMonth, List<TransferBetweenAccountsCommand>> entry : transfers.entrySet()) {
            if (!entry.getKey().isAfter(selectedMonth)) {
                for (TransferBetweenAccountsCommand command : entry.getValue()) {
                    if (command.IsSourceAccount(this)) {
                        totalTransfers -= command.GetAmount();
                    } else {
                        totalTransfers += command.GetAmount();
                    }
                }
            }
        }

        double newValue = totalDeposits - totalExpenses + totalTransfers;
        setValue(newValue);
    }



    @Override
    public EntityType GetType(){
        return EntityType.ACCOUNT;
    }
}
