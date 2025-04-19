package com.rostyslavliapkin.spendingbuddy;

import com.rostyslavliapkin.spendingbuddy.controllers.AccountsController;
import com.rostyslavliapkin.spendingbuddy.controllers.ExpensesController;
import com.rostyslavliapkin.spendingbuddy.controllers.IncomesController;
import com.rostyslavliapkin.spendingbuddy.core.Account;
import com.rostyslavliapkin.spendingbuddy.core.Expense;
import com.rostyslavliapkin.spendingbuddy.core.Income;
import com.rostyslavliapkin.spendingbuddy.core.ResourceEntity;
import com.rostyslavliapkin.spendingbuddy.utils.FileManager;
import com.rostyslavliapkin.spendingbuddy.utils.ViewGenerator;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import java.util.Objects;


public class MainTabController {

    @FXML
    private FlowPane incomesContainer;

    @FXML
    private FlowPane accountsContainer;

    @FXML
    private FlowPane expensesContainer;

    @FXML
    public void initialize() {
        UpdateView();
    }


    public void UpdateView(){
        Image image = new Image(Objects.requireNonNull(getClass().getResource("/images/plus.png")).toExternalForm());

        // Add incomes to a container
        incomesContainer.getChildren().clear();
        for (Income income : IncomesController.GetIncomes()){
            incomesContainer.getChildren().add(ViewGenerator.createResourceEntityView(income));
        }
        // Create a button to add a new Income
        Button addIncome = CreateAddButton(image);
        addIncome.setOnAction(actionEvent -> {
            handleAddNewEntity("Add new Income", ResourceEntity.EntityType.INCOME);
        });
        incomesContainer.getChildren().add(addIncome);


        // Add accounts to a container
        accountsContainer.getChildren().clear();
        for (Account account: AccountsController.GetAccounts()){
            accountsContainer.getChildren().add(ViewGenerator.createResourceEntityView(account));
        }
        // Create a button to add new account
        Button addAccount = CreateAddButton(image);
        addAccount.setOnAction(actionEvent -> {
            handleAddNewEntity("Add new Account", ResourceEntity.EntityType.ACCOUNT);
        });
        accountsContainer.getChildren().add(addAccount);

        // Add Expenses to a container
        expensesContainer.getChildren().clear();
        for (Expense expense: ExpensesController.GetExpenses()){
            expensesContainer.getChildren().add(ViewGenerator.createResourceEntityView(expense));
        }
        // Create a button to add new Expenses
        Button addExpenses = CreateAddButton(image);
        addExpenses.setOnAction(actionEvent -> {
            handleAddNewEntity("Add new Expense", ResourceEntity.EntityType.EXPENSE);
        });
        expensesContainer.getChildren().add(addExpenses);
    }

    private Button CreateAddButton(Image image){
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(60);
        imageView.setFitHeight(60);
        Button addButton = new Button();
        addButton.setMinWidth(90);
        addButton.setMinHeight(90);
        addButton.setGraphic(imageView);
        return addButton;
    }

    private void handleAddNewEntity(String title, ResourceEntity.EntityType type){
        try {
            PopUPGenerator.ShowAddEntityPopup(title, type);
            UpdateView();
            FileManager.SaveToJson();
        }catch (Exception _){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Something went wrong", ButtonType.OK);
            alert.show();
        }
    }

}
