package com.rostyslavliapkin.spendingbuddy.controllers;

import com.rostyslavliapkin.spendingbuddy.core.Income;

import java.util.ArrayList;
import java.util.List;

public class IncomesController {
    private static List<Income> incomes = new ArrayList<>();

    public static void CreateDefaultIncomes() {
        incomes = new ArrayList<>();
        incomes.add(new Income("Salary", AccountsController.class.getResource("/images/salary.png")));
        incomes.add(new Income("Scholarship", AccountsController.class.getResource("/images/scholarship.png")));
    }

    public static void ClearIncomes(){
        incomes.clear();
    }

    public static void AddIncome(Income income){
        incomes.add(income);
    }

    public static Income GetIncomeByName(String name){
        for(Income income : incomes){
            if (income.getName().equals(name))
                return income;
        }
        return null;
    }


    public static List<Income> GetIncomes(){
        return incomes;
    }
}
