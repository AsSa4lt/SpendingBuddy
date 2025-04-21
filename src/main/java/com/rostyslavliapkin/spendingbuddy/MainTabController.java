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

/**
 * Controller for managing the main view of the Spending Buddy application.
 * Handles the display of incomes, accounts, and expenses, as well as adding new entries.
 */
public class MainTabController {

    /**
     * Container for displaying income entries in the UI.
     */
    @FXML
    private FlowPane incomesContainer;

    /**
     * Container for displaying account entries in the UI.
     */
    @FXML
    private FlowPane accountsContainer;

    /**
     * Container for displaying expense entries in the UI.
     */
    @FXML
    private FlowPane expensesContainer;

    /**
     * Initializes the main tab view by updating it with the current data.
     */
    @FXML
    public void initialize() {
        UpdateView();
    }

    /**
     * Updates the view with the data for incomes, accounts, and expenses.ion.
     */
    public void UpdateView(){
        Image image = new Image(Objects.requireNonNull(getClass().getResource("/images/plus.png")).toExternalForm());

        // Add incomes to a container
        incomesContainer.getChildren().clear();
        for (Income income : IncomesController.GetIncomes()){
            incomesContainer.getChildren().add(ViewGenerator.CreateResourceEntityView(income));
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
            accountsContainer.getChildren().add(ViewGenerator.CreateResourceEntityView(account));
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
            expensesContainer.getChildren().add(ViewGenerator.CreateResourceEntityView(expense));
        }
        // Create a button to add new Expenses
        Button addExpenses = CreateAddButton(image);
        addExpenses.setOnAction(actionEvent -> {
            handleAddNewEntity("Add new Expense", ResourceEntity.EntityType.EXPENSE);
        });
        expensesContainer.getChildren().add(addExpenses);
    }

    /**
     * Creates a button to add a new entity (income, account, or expense) to the UI.
     * The button is designed with an image of a plus sign.
     *
     * @param image The image to be displayed on the button (a plus sign).
     * @return The created button.
     */
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

    /**
     * Handles the action of adding a new entity (income, account, or expense).
     * This method shows a popup for adding the entity, updates the view, and saves the changes to a file.
     *
     * @param title The title of the popup.
     * @param type The type of the entity to be added (income, account, or expense).
     */
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
