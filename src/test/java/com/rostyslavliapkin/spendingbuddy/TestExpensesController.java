package com.rostyslavliapkin.spendingbuddy;

import com.rostyslavliapkin.spendingbuddy.controllers.ExpensesController;
import com.rostyslavliapkin.spendingbuddy.core.Expense;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class TestExpensesController {
    private Expense expenseFirst;
    private Expense expenseSecond;

    /**
     * Init expenses and clear controller
     */
    @BeforeEach
    public void SetUp(){
        expenseFirst = new Expense("Expense 1", ExpensesController.class.getResource("/images/bankCard.png"));
        expenseSecond = new Expense("Expense 2", ExpensesController.class.getResource("/images/bankCard.png"));
        ExpensesController.ClearExpenses();
    }

    /**
     * Tests filling ExpenseController with default entities
     */
    @Test
    public void TestDefaultExpenses(){
        ExpensesController.CreateDefaultExpenses();
        assertFalse(ExpensesController.GetExpenses().isEmpty());
    }

    /**
     * Tests adding new expenses
     */
    @Test
    public void TestAddingExpenses(){
        ExpensesController.AddExpense(expenseFirst);
        ExpensesController.AddExpense(expenseSecond);
        assertEquals(2, ExpensesController.GetExpenses().size());
    }

    /**
     * Tests getting expense by name
     */
    @Test
    public void TestRemoveExpense(){
        ExpensesController.AddExpense(expenseFirst);
        ExpensesController.AddExpense(expenseSecond);
        ExpensesController.RemoveExpense(expenseFirst);
        assertEquals(1, ExpensesController.GetExpenses().size());
    }

    /**
     * Tests name availability
     */
    @Test
    public void TestIsNameAvailable(){
        ExpensesController.AddExpense(expenseFirst);
        assertFalse(ExpensesController.IsNameAvailable(expenseFirst.getName()));
    }

}
