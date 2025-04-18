package com.rostyslavliapkin.spendingbuddy.controllers;

import com.rostyslavliapkin.spendingbuddy.core.Expense;

import java.util.ArrayList;
import java.util.List;

public class ExpensesController {
    private static List<Expense> expenses = new ArrayList<>();

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

    public static void ClearExpenses(){
        expenses.clear();
    }

    public static void AddExpense(Expense expense){
        expenses.add(expense);
    }

    public static Expense GetExpenseByName(String name){
        for(Expense expense : expenses){
            if (expense.getName().equals(name))
                return expense;
        }
        return null;
    }

    public static List<Expense> GetExpenses(){
        return expenses;
    }
}
