package com.rostyslavliapkin.spendingbuddy.controllers;

import com.rostyslavliapkin.spendingbuddy.core.Account;
import com.rostyslavliapkin.spendingbuddy.core.Expense;
import com.rostyslavliapkin.spendingbuddy.core.Income;
import com.rostyslavliapkin.spendingbuddy.core.commands.Command;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that controls all incomes and provides some basic functions
 */
public class IncomesController {
    /**
     * List of all incomes
     */
    private static List<Income> incomes = new ArrayList<>();

    /**
     * Function to create some basic types of incomes if there are no user data
     */
    public static void CreateDefaultIncomes() {
        incomes = new ArrayList<>();
        incomes.add(new Income("Salary", AccountsController.class.getResource("/images/salary.png")));
        incomes.add(new Income("Scholarship", AccountsController.class.getResource("/images/scholarship.png")));
    }

    /**
     * Function to clear all incomes
     */
    public static void ClearIncomes(){
        incomes.clear();
    }

    /**
     * Function to remove command from all incomes
     * @param command to be removed
     */
    public static void RemoveCommand(Command command){
        for(Income income : incomes){
            income.RemoveCommand(command);
        }
    }

    /**
     * Function to remove income from list of all incomes and from commands
     * @param removedIncome income to be removed
     */
    public static void RemoveIncome(Income removedIncome){
        incomes.remove(removedIncome);
        for(Income income : incomes){
            income.RemoveIncome(removedIncome);
        }
        for(Account account: AccountsController.GetAccounts()){
            account.RemoveIncome(removedIncome);
        }
    }

    /**
     * Function to add income to the list of incomes
     * @param income to be added
     */
    public static void AddIncome(Income income){
        incomes.add(income);
    }

    /**
     * Function to get income by a name
     * @param name to filter income
     * @return Income if there is any, or null if no income with this name
     */
    public static Income GetIncomeByName(String name){
        for(Income income : incomes){
            if (income.getName().equals(name))
                return income;
        }
        return null;
    }


    /**
     * Function to get a list of all incomes
     * @return list of incomes
     */
    public static List<Income> GetIncomes(){
        return incomes;
    }
}
