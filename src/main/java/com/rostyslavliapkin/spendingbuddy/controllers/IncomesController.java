package com.rostyslavliapkin.spendingbuddy.controllers;

import com.rostyslavliapkin.spendingbuddy.core.Income;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class IncomesController {
    private static List<Income> incomes;

    public static void CreateDefaultIncomes() {
        incomes = new ArrayList<>();
        URL imageUrl1 = AccountsController.class.getResource("/images/salary.png");
        URL imageUrl2 = AccountsController.class.getResource("/images/scholarship.png");
        Income income1 = new Income("Salary", imageUrl1);
        Income income2 = new Income("Scholarship", imageUrl2);
        incomes.add(income1);
        incomes.add(income2);
    }

    public static List<Income> GetIncomes(){
        return incomes;
    }
}
