package com.rostyslavliapkin.spendingbuddy;

import com.rostyslavliapkin.spendingbuddy.controllers.AppController;
import com.rostyslavliapkin.spendingbuddy.controllers.ExpensesController;
import com.rostyslavliapkin.spendingbuddy.core.commands.SpendingCommand;
import com.rostyslavliapkin.spendingbuddy.core.commands.Command;

import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.util.Pair;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;

public class ReportTabController {

    @FXML
    private PieChart expensePieChart;

    @FXML
    public void initialize() { }


    public void LoadExpenseDataForCurrentMonth() {
        YearMonth currentMonth = AppController.SelectedYearMonth;

        List<Pair<String, Double>> stats = ExpensesController.GetStatsForMonth(currentMonth);

        List<PieChart.Data> pieChartData = new ArrayList<>();
        for (Pair<String, Double> pair : stats) {
            if (pair.getValue() > 0.01) {
                pieChartData.add(new PieChart.Data(pair.getKey(), pair.getValue()));
            }
        }

        expensePieChart.setData(javafx.collections.FXCollections.observableArrayList(pieChartData));
        expensePieChart.setTitle("Spendings for " + currentMonth.getMonth().name());
    }
}
