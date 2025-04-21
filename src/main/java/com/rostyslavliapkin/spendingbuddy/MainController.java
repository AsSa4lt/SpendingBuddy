package com.rostyslavliapkin.spendingbuddy;

import com.rostyslavliapkin.spendingbuddy.controllers.AppController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.YearMonth;

/**
 * Controller for the main view of the Spending Buddy application.
 * Manages the tab-based UI and handles the coordination of monthly/yearly filters,
 * as well as loading and refreshing different views (Main, History, Report, Settings).
 */
public class MainController {
    /**
     * ComboBox used to select a month for report filtering.
     */
    @FXML
    public ComboBox<Month> monthComboBox;

    /**
     * ComboBox used to select a year for report filtering.
     */
    @FXML
    public ComboBox<Integer> yearComboBox;

    /**
     * The main tab pane containing all application tabs.
     */
    @FXML
    private TabPane tabPane;

    /**
     * Controller of the main tab
     */
    private MainTabController mainTabController;

    /**
     * Controller for settings tab
     */
    private SettingsTabController settingsTabController;

    /**
     * Controller for history tab
     */
    private HistoryTabController historyTabController;

    /**
     * Controller for report tab
     */
    private ReportTabController reportTabController;


    /**
     * Initializes the main controller.
     * Sets up the tab views, listeners for tab changes and key events,
     * and initializes the year/month ComboBoxes for report filtering.
     */
    @FXML
    public void initialize() {
        try {
            // Load main tab
            FXMLLoader mainTabLoader = new FXMLLoader(getClass().getResource("main_tab.fxml"));
            Parent mainTabContent = mainTabLoader.load();
            mainTabController = mainTabLoader.getController();
            AppController.MainTab = mainTabController;
            Tab mainTab = new Tab("Main", mainTabContent);
            mainTab.setClosable(false);
            tabPane.getTabs().add(mainTab);

            // Load history tab
            FXMLLoader historyTabLoader = new FXMLLoader(getClass().getResource("history_tab.fxml"));
            Parent historyTabContent = historyTabLoader.load();
            historyTabController = historyTabLoader.getController();
            Tab historyTab = new Tab("History", historyTabContent);
            historyTab.setClosable(false);
            tabPane.getTabs().add(historyTab);
            historyTabController.SetCommandsManager(AppController.GetCommandsManager());
            historyTab.setOnSelectionChanged(event -> {
                if (historyTab.isSelected()) {
                    historyTabController.RenderHistory();
                }
            });
            historyTabContent.setOnKeyReleased(event -> {
                historyTabController.RenderHistory();
            });

            // Load report tab
            FXMLLoader reportTabLoader = new FXMLLoader(getClass().getResource("report_tab.fxml"));
            Parent reportTabContent = reportTabLoader.load();
            reportTabController = reportTabLoader.getController();
            Tab reportTab = new Tab("Report", reportTabContent);
            reportTab.setClosable(false);
            tabPane.getTabs().add(reportTab);
            reportTab.setOnSelectionChanged(event -> {
                if (reportTab.isSelected()) {
                    reportTabController.LoadExpenseDataForCurrentMonth();
                }
            });
            reportTabContent.setOnKeyReleased(event -> {
                reportTabController.LoadExpenseDataForCurrentMonth();
            });

            // Load settings tab
            FXMLLoader settingsTabLoader = new FXMLLoader(getClass().getResource("settings_tab.fxml"));
            Parent settingsTabContent = settingsTabLoader.load();
            settingsTabController = settingsTabLoader.getController();
            Tab settingsTab = new Tab("Settings", settingsTabContent);
            settingsTab.setClosable(false);
            tabPane.getTabs().add(settingsTab);


            monthComboBox.getItems().addAll(Month.values());
            monthComboBox.setValue(LocalDate.now().getMonth());
            int currentYear = Year.now().getValue();
            for(int i = currentYear - 10; i <= currentYear; i++){
                yearComboBox.getItems().add(i);
            }
            yearComboBox.setValue(currentYear);
            monthComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
                updateSelectedYearMonth();
                reportTabController.LoadExpenseDataForCurrentMonth();
            });

            yearComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
                updateSelectedYearMonth();
                reportTabController.LoadExpenseDataForCurrentMonth();
            });

            updateSelectedYearMonth();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates the selected month and year in the {@link AppController}
     * based on the values in the ComboBoxes.
     */
    private void updateSelectedYearMonth() {
        Month selectedMonth = (Month) monthComboBox.getValue();
        int selectedYear = (int) yearComboBox.getValue();

        YearMonth selectedYearMonth = YearMonth.of(selectedYear, selectedMonth);
        AppController.UpdateSelectedMonth(selectedYearMonth);
    }

}
