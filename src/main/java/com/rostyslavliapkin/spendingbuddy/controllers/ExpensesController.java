package com.rostyslavliapkin.spendingbuddy.controllers;

import com.rostyslavliapkin.spendingbuddy.core.Expense;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ExpensesController {
    private static List<Expense> expenses;

    public static void CreateDefaultExpenses() {
        expenses = new ArrayList<>();
        expenses.add(new Expense("Food", AccountsController.class.getResource("/images/food.png")));
        expenses.add(new Expense("Groceries", AccountsController.class.getResource("/images/groceries.png")));
        expenses.add(new Expense("Household", AccountsController.class.getResource("/images/household.png")));
        expenses.add(new Expense("Sport", AccountsController.class.getResource("/images/sport.png")));
        expenses.add(new Expense("Entertainment", AccountsController.class.getResource("/images/entertainment.png")));
    }

    public static List<Expense> GetExpenses(){
        return expenses;
    }
}
