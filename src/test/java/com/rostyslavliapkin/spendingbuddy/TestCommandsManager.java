package com.rostyslavliapkin.spendingbuddy;

import com.rostyslavliapkin.spendingbuddy.controllers.AccountsController;
import com.rostyslavliapkin.spendingbuddy.core.Account;
import com.rostyslavliapkin.spendingbuddy.core.Expense;
import com.rostyslavliapkin.spendingbuddy.core.Income;
import com.rostyslavliapkin.spendingbuddy.core.commands.Command;
import com.rostyslavliapkin.spendingbuddy.core.commands.DepositCommand;
import com.rostyslavliapkin.spendingbuddy.core.commands.SpendingCommand;
import com.rostyslavliapkin.spendingbuddy.managers.CommandsManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class TestCommandsManager {
    private CommandsManager manager;
    private Account account;
    private Income income;
    private Expense expense;
    private DepositCommand depositCommand;
    private SpendingCommand spendingCommand;
    /**
     * Initializes commands manager and adds a few commands
     */
    @BeforeEach
    public void SetUp(){
        manager = new CommandsManager();
        account = new Account("Account 1", AccountsController.class.getResource("/images/bankCard.png"));
        income = new Income("Income 1", AccountsController.class.getResource("/images/bankCard.png"));
        expense = new Expense("Expense 1", AccountsController.class.getResource("/images/bankCard.png"));
        depositCommand = new DepositCommand(income, account, 500, YearMonth.now());
        spendingCommand = new SpendingCommand(account, expense, 500, YearMonth.now());
    }

    @Test
    public void TestCommandExecuted(){
        manager.ExecuteCommand(depositCommand);
        manager.ExecuteCommand(spendingCommand);
        List<Command> commands = manager.getCommandsForDate(LocalDate.now());
        assertEquals(2, commands.size());
    }

    @Test
    public void TestUndoCommand(){
        manager.ExecuteCommand(depositCommand);
        manager.ExecuteCommand(spendingCommand);
        manager.UndoLastCommand();
        assertEquals(1, manager.getCommandsForDate(LocalDate.now()).size());
        manager.UndoLastCommand();
        assertEquals(0, manager.getCommandsForDate(LocalDate.now()).size());
    }

    @Test
    public void TestEmptyManager(){
        assertEquals(0, manager.getCommandsForDate(LocalDate.now()).size());
    }

    @Test
    public void TestRemoveEntity(){
        manager.ExecuteCommand(depositCommand);
        manager.RemoveEntity(account);
        assertEquals(0, manager.getCommandsForDate(LocalDate.now()).size());
    }

    @Test
    public void TestRemoveCommand(){
        manager.ExecuteCommand(depositCommand);
        manager.RemoveCommand(depositCommand);
        assertEquals(0, manager.getCommandsForDate(LocalDate.now()).size());
    }

    @Test
    public void TestManyCommands(){
        for (int i = 0; i < 100; i++){
            manager.ExecuteCommand(depositCommand);
        }
        assertEquals(100, manager.getCommandsForDate(LocalDate.now()).size());
        for (int i = 0; i < 100; i++){
            manager.UndoLastCommand();
        }
        assertEquals(0, manager.getCommandsForDate(LocalDate.now()).size());
    }

    @Test
    public void TestRemoveCommandAndUndo(){
        manager.ExecuteCommand(depositCommand);
        manager.RemoveCommand(depositCommand);
        manager.UndoLastCommand();
        assertEquals(0, manager.getCommandsForDate(LocalDate.now()).size());
    }

    @Test
    public void TestRemoveEntityAndUndo(){
        manager.ExecuteCommand(depositCommand);
        manager.RemoveEntity(account);
        manager.UndoLastCommand();
        assertEquals(0, manager.getCommandsForDate(LocalDate.now()).size());
    }

}
