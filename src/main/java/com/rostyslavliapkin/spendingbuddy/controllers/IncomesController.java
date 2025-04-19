package com.rostyslavliapkin.spendingbuddy.controllers;

import com.rostyslavliapkin.spendingbuddy.core.Income;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class IncomesController {
    private static List<Income> incomes = new ArrayList<>();

    public static void CreateDefaultIncomes() {
        incomes = new ArrayList<>();
        URL imageUrl1 = AccountsController.class.getResource("/images/salary.png");
        URL imageUrl2 = AccountsController.class.getResource("/images/scholarship.png");
        Income income1 = new Income("Salary", imageUrl1);
        Income income2 = new Income("Scholarship", imageUrl2);
        incomes.add(income1);
        incomes.add(income2);
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
