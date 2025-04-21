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
 * Class that represents the account the user where money are stored
 */
public class Account extends ResourceEntity {
    /**
     * Map of all deposits to this account
     */
    private final Map<YearMonth, List<DepositCommand>> deposits;

    /**
     * Map of all expenses from this account
     */
    private final Map<YearMonth, List<SpendingCommand>> expenses;

    /**
     * Map of all transfers to and from this account
     */
    private final Map<YearMonth, List<TransferCommand>> transfers;

    /**
     * Constructs a new account
     * @param name
     * @param imageUrl
     */
    public Account(String name, URL imageUrl) {
        super(name, imageUrl);
        deposits = new HashMap<>();
        expenses = new HashMap<>();
        transfers = new HashMap<>();
    }

    /**
     * Removes the command from all lists by undoing it
     * @param command to be undone
     */
    public void RemoveCommand(Command command) {
        if (command instanceof DepositCommand) {
            UndoDeposit((DepositCommand) command);
        } else if (command instanceof SpendingCommand) {
            UndoSpend((SpendingCommand) command);
        } else if (command instanceof TransferCommand) {
            UndoAccountTransfer((TransferCommand) command);
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

        // Remove any DepositCommands associated with this account
        for (List<DepositCommand> depositList : deposits.values()) {
            depositList.removeIf(cmd -> cmd.GetAccount().equals(account));
        }

        // Remove any TransferCommands where this account is either source or destination
        for (List<TransferCommand> transferList : transfers.values()) {
            transferList.removeIf(cmd ->
                    cmd.GetSourceAccount().equals(account) || cmd.GetTargetAccount().equals(account)
            );
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
     * Removes all mentions of income from commands
     * @param income to be removed
     */
    public void RemoveIncome(Income income) {
        // Remove any SpendingCommands associated with this account
        for (List<DepositCommand> depositList : deposits.values()) {
            depositList.removeIf(cmd -> cmd.GetIncome().equals(income));
        }
    }


    /**
     * Deposits money from income
     * @param command deposit to be done
     * @return success of deposit command
     */
    public boolean Deposit(DepositCommand command){
        List<DepositCommand> list = deposits.computeIfAbsent(command.GetYearMonth(), _ -> new ArrayList<>());
        list.add(command);
        UpdateFromYearMonth(AppController.SelectedYearMonth);
        return true;
    }

    /**
     * Undoes the deposit for this account
     * @param command deposit to be undone
     * @return success of undoing the deposit command
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
     * Spends money from the account
     * @param command to be done
     * @return success of spending the money
     */
    public boolean Spend(SpendingCommand command){
        List<SpendingCommand> list = expenses.computeIfAbsent(command.GetYearMonth(), _ -> new ArrayList<>());
        list.add(command);
        UpdateFromYearMonth(AppController.SelectedYearMonth);
        return true;
    }

    /**
     * Undoes the spending of money from the account
     * @param command to be undone
     * @return success of undoing the spending of money
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
     * Transfers or receives money from another account
     * @param command
     * @return
     */
    public boolean AccountTransfer(TransferCommand command) {
        List<TransferCommand> list = transfers.computeIfAbsent(command.GetYearMonth(), _ -> new ArrayList<>());
        list.add(command);
        UpdateFromYearMonth(AppController.SelectedYearMonth);
        return true;
    }

    /**
     * Undoes the transfer of money between accounts
     * @param command to be undone
     * @return success of undoing to transfer
     */
    public boolean UndoAccountTransfer(TransferCommand command) {
        List<TransferCommand> list = transfers.get(command.GetYearMonth());
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
        if(selectedMonth == null)
            return;

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

        for (Map.Entry<YearMonth, List<TransferCommand>> entry : transfers.entrySet()) {
            if (!entry.getKey().isAfter(selectedMonth)) {
                for (TransferCommand command : entry.getValue()) {
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


    /**
     * @return a type of account
     */
    @Override
    public EntityType GetType(){
        return EntityType.ACCOUNT;
    }
}
