package com.rostyslavliapkin.spendingbuddy.controllers;

import com.rostyslavliapkin.spendingbuddy.core.Account;
import com.rostyslavliapkin.spendingbuddy.core.Expense;
import com.rostyslavliapkin.spendingbuddy.core.Income;
import com.rostyslavliapkin.spendingbuddy.core.commands.Command;
import javafx.util.Pair;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that controls all expenses and provides some basic functions
 */
public class ExpensesController {
    /**
     * List to store all expenses of the user
     */
    private static List<Expense> expenses = new ArrayList<>();

    /**
     * Function to fill default expenses for the user uf there are no created
     */
    public static void CreateDefaultExpenses() {
        expenses = new ArrayList<>();
        expenses.add(new Expense("Food", AccountsController.class.getResource("/images/food.png")));
        expenses.add(new Expense("Groceries", AccountsController.class.getResource("/images/groceries.png")));
        expenses.add(new Expense("Household", AccountsController.class.getResource("/images/household.png")));
        expenses.add(new Expense("Sport", AccountsController.class.getResource("/images/sport.png")));
        expenses.add(new Expense("Entertainment", AccountsController.class.getResource("/images/entertainment.png")));
        expenses.add(new Expense("MHD", AccountsController.class.getResource("/images/bus.png")));
        expenses.add(new Expense("Medicine", AccountsController.class.getResource("/images/heart.png")));
        expenses.add(new Expense("Pet", AccountsController.class.getResource("/images/dog.png")));
        expenses.add(new Expense("Car", AccountsController.class.getResource("/images/car.png")));
        expenses.add(new Expense("Vacation", AccountsController.class.getResource("/images/plane.png")));
    }

    /**
     * Function to clear expeneses of the user
     */
    public static void ClearExpenses(){
        expenses.clear();
    }

    /**
     * Function to add a new expense for user
     * @param expense to be added
     */
    public static void AddExpense(Expense expense){
        expenses.add(expense);
    }

    /**
     * @param name to check
     * @return checks and returns if name is available
     */
    public static boolean IsNameAvailable(String name){
        for (Expense expense: expenses){
            if (expense.getName().equals(name))
                return false;
        }
        return true;
    }

    /**
     * Function to remove a command from expenses
     * @param command to be removed
     */
    public static void RemoveCommand(Command command){
        for(Expense expense : expenses){
            expense.RemoveCommand(command);
        }
    }

    /**
     * Function to remove expense from all lists of commands
     * @param removedExpense expense to be removed
     */
    public static void RemoveExpense(Expense removedExpense){
        expenses.remove(removedExpense);
        for(Expense expense: expenses){
            expense.RemoveExpense(removedExpense);
        }
        for(Account account : AccountsController.GetAccounts()){
            account.RemoveExpense(removedExpense);
        }
    }

    /**
     * Get stats for expenses for selected month
     * @param yearMonth to filter expenses values
     * @return All Stats for current month
     */
    public static List<Pair<String, Double>> GetStatsForMonth(YearMonth yearMonth){
        List<Pair<String, Double>> stats = new ArrayList<>();
        for (Expense expense: expenses){
            stats.add(new Pair<>(expense.getName(), expense.GetValueForYearMonth(yearMonth)));
        }
        return stats;
    }

    /**
     * Function to get expenses by Name
     * @param name to find expense
     * @return Expense with provided name or null if there is no Expense
     */
    public static Expense GetExpenseByName(String name){
        for(Expense expense : expenses){
            if (expense.getName().equals(name))
                return expense;
        }
        return null;
    }

    /**
     * Function to get all expenses
     * @return a list of all expenses
     */
    public static List<Expense> GetExpenses(){
        return expenses;
    }
}
