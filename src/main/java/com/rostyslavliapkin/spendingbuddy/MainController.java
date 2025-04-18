package com.rostyslavliapkin.spendingbuddy;

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
            monthComboBox.setValue(LocalDate.now().getMonth().name());
            int currentYear = Year.now().getValue();
            for(int i = currentYear - 10; i <= currentYear; i++){
                yearComboBox.getItems().add(i);
            }
            yearComboBox.setValue(currentYear);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
