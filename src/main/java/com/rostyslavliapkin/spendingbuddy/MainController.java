package com.rostyslavliapkin.spendingbuddy;

import com.rostyslavliapkin.spendingbuddy.controllers.AppController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.YearMonth;

public class MainController {
    public ComboBox monthComboBox;
    public ComboBox yearComboBox;
    private MainTabController mainTabController;
    private SettingsTabController settingsTabController;

    @FXML
    private TabPane tabPane;

    @FXML
    public void initialize() {
        try {
            // Load main tab
            FXMLLoader mainTabLoader = new FXMLLoader(getClass().getResource("main_tab.fxml"));
            Parent mainTabContent = mainTabLoader.load();
            mainTabController = mainTabLoader.getController();
            Tab mainTab = new Tab("Main", mainTabContent);
            mainTab.setClosable(false);
            tabPane.getTabs().add(mainTab);

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
            });

            yearComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
                updateSelectedYearMonth();
            });

            updateSelectedYearMonth();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateSelectedYearMonth() {
        Month selectedMonth = (Month) monthComboBox.getValue();
        int selectedYear = (int) yearComboBox.getValue();

        YearMonth selectedYearMonth = YearMonth.of(selectedYear, selectedMonth);
        AppController.UpdateSelectedMonth(selectedYearMonth);
    }

}
