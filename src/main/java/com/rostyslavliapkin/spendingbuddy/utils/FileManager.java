package com.rostyslavliapkin.spendingbuddy.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.rostyslavliapkin.spendingbuddy.controllers.*;
import com.rostyslavliapkin.spendingbuddy.core.Account;
import com.rostyslavliapkin.spendingbuddy.core.Expense;
import com.rostyslavliapkin.spendingbuddy.core.Income;
import com.rostyslavliapkin.spendingbuddy.core.commands.*;
import com.rostyslavliapkin.spendingbuddy.serializable.*;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

public class FileManager {

    public static class DataWrapper {
        public String selectedCurrency;
        public List<AccountSerializable> accounts = new ArrayList<>();
        public List<IncomeSerializable> incomes = new ArrayList<>();
        public List<ExpenseSerializable> expenses = new ArrayList<>();
        public Map<LocalDate, List<CommandSerializableWrapper>> commands = new HashMap<>();
    }

    public static class CommandSerializableWrapper {
        public String type;
        public Object data;

        public CommandSerializableWrapper() {}

        public CommandSerializableWrapper(String type, Object data) {
            this.type = type;
            this.data = data;
        }
    }

    public static void SaveToJson() {
        DataWrapper wrapper = new DataWrapper();

        // Save selected currency
        wrapper.selectedCurrency = SettingsController.SelectedCurrency;

        // Serialize Accounts
        for (Account account : AccountsController.GetAccounts()) {
            wrapper.accounts.add(new AccountSerializable(account));
        }

        // Serialize Incomes
        for (Income income : IncomesController.GetIncomes()) {
            wrapper.incomes.add(new IncomeSerializable(income));
        }

        // Serialize Expenses
        for (Expense expense : ExpensesController.GetExpenses()) {
            wrapper.expenses.add(new ExpenseSerializable(expense));
        }

        // Serialize Commands
        for (Map.Entry<LocalDate, List<Command>> entry : AppController.GetCommandsManager().allCommands.entrySet()) {
            List<CommandSerializableWrapper> commandList = new ArrayList<>();
            for (Command command : entry.getValue()) {
                if (command instanceof SpendingCommand) {
                    commandList.add(new CommandSerializableWrapper("Spending", new SpendingCommandSerializable((SpendingCommand) command)));
                } else if (command instanceof DepositCommand) {
                    commandList.add(new CommandSerializableWrapper("Deposit", new DepositCommandSerializable((DepositCommand) command)));
                } else if (command instanceof TransferCommand) {
                    commandList.add(new CommandSerializableWrapper("Transfer", new TransferCommandSerializable((TransferCommand) command)));
                }
            }
            wrapper.commands.put(entry.getKey(), commandList);
        }

        // Write to JSON file
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        try {
            mapper.writeValue(new File("app_data.json"), wrapper);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void LoadFromJson() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        try {
            File file = new File("app_data.json");
            if (!file.exists()) {
                AppController.CreateDefaultResourceEntities();
                System.out.println("No saved data found.");
                return;
            }

            DataWrapper wrapper = mapper.readValue(file, DataWrapper.class);

            SettingsController.SelectedCurrency = wrapper.selectedCurrency;
            AccountsController.ClearAccounts();
            IncomesController.ClearIncomes();
            ExpensesController.ClearExpenses();
            AppController.GetCommandsManager().allCommands.clear();

            // Load Accounts
            for (AccountSerializable accountSerializable : wrapper.accounts) {
                AccountsController.AddAccount(accountSerializable.ToAccount());
            }

            // Load Incomes
            for (IncomeSerializable incomeSerializable : wrapper.incomes) {
                IncomesController.AddIncome(incomeSerializable.ToIncome());
            }

            // Load Expenses
            for (ExpenseSerializable expenseSerializable : wrapper.expenses) {
                ExpensesController.AddExpense(expenseSerializable.ToExpense());
            }

            // Load Commands
            for (Map.Entry<LocalDate, List<CommandSerializableWrapper>> entry : wrapper.commands.entrySet()) {
                List<Command> commands = new ArrayList<>();
                for (CommandSerializableWrapper cmdWrapper : entry.getValue()) {
                    switch (cmdWrapper.type) {
                        case "Spending":
                            commands.add(((SpendingCommandSerializable) mapper.convertValue(cmdWrapper.data, SpendingCommandSerializable.class)).ToSpendingCommand());
                            break;
                        case "Deposit":
                            commands.add(((DepositCommandSerializable) mapper.convertValue(cmdWrapper.data, DepositCommandSerializable.class)).ToDepositCommand());
                            break;
                        case "Transfer":
                            commands.add(((TransferCommandSerializable) mapper.convertValue(cmdWrapper.data, TransferCommandSerializable.class)).ToTransferCommand());
                            break;
                    }
                }
                AppController.GetCommandsManager().allCommands.put(entry.getKey(), commands);
                for (Command command : commands) {
                    command.Execute();
                }
            }

            System.out.println("App data successfully loaded!");

        } catch (IOException e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }

    public static boolean DeleteDataFile() {
        File file = new File("app_data.json");
        if (file.exists()) {
            boolean deleted = file.delete();
            if (deleted) {
                System.out.println("Data file deleted successfully.");
            } else {
                System.out.println("Failed to delete data file.");
            }
            return deleted;
        } else {
            System.out.println("Data file does not exist.");
            return false;
        }
    }
}
