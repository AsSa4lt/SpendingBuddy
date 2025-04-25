package com.rostyslavliapkin.spendingbuddy;

import com.rostyslavliapkin.spendingbuddy.controllers.IncomesController;
import com.rostyslavliapkin.spendingbuddy.core.Income;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class TestIncomesController {
    private Income incomeFirst;
    private Income incomeSecond;

    /**
     * Init incomes and clear controller
     */
    @BeforeEach
    public void SetUp(){
        incomeFirst = new Income("Income 1", IncomesController.class.getResource("/images/bankCard.png"));
        incomeSecond = new Income("Income 2", IncomesController.class.getResource("/images/bankCard.png"));
        IncomesController.ClearIncomes();
    }

    /**
     * Tests Filling IncomeController with default entities
     */
    @Test
    public void TestDefaultIncomes(){
        IncomesController.CreateDefaultIncomes();
        assertFalse(IncomesController.GetIncomes().isEmpty());
    }

    /**
     * Tests adding new incomes
     */
    @Test
    public void TestAddingIncomes(){
        IncomesController.AddIncome(incomeFirst);
        IncomesController.AddIncome(incomeSecond);
        assertEquals(2, IncomesController.GetIncomes().size());
    }

    /**
     * Tests getting income by name
     */
    @Test
    public void TestGetByName(){
        IncomesController.AddIncome(incomeFirst);
        IncomesController.AddIncome(incomeSecond);
        assertEquals(incomeFirst, IncomesController.GetIncomeByName(incomeFirst.getName()));
    }

    /**
     * Tests removing income
     */
    @Test
    public void TestRemoveIncome(){
        IncomesController.AddIncome(incomeFirst);
        IncomesController.AddIncome(incomeSecond);
        IncomesController.RemoveIncome(incomeFirst);
        assertEquals(1, IncomesController.GetIncomes().size());
    }

    /**
     * Tests name availability
     */
    @Test
    public void TestIsNameAvailable(){
        IncomesController.AddIncome(incomeFirst);
        assertFalse(IncomesController.IsNameAvailable(incomeFirst.getName()));
    }
}
