package com.rostyslavliapkin.spendingbuddy;

import com.rostyslavliapkin.spendingbuddy.controllers.AppController;
import com.rostyslavliapkin.spendingbuddy.controllers.ExpensesController;

import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.util.Pair;
import java.time.YearMonth;
import java.util.*;

/**
 * Controller for the Report Tab of the application. This class handles the generation and
 * display of a pie chart showing the expense distribution for the current month.
 * The data is fetched from the ExpensesController and displayed as a pie chart.
 */
public class ReportTabController {

    /**
     * The PieChart UI component that displays the expense data for the current month.
     */
    @FXML
    private PieChart expensePieChart;

    /**
     * Initializes the controller. Currently empty as there are no initializations needed
     * during the setup.
     */
    @FXML
    public void initialize() { }

    /**
     * Loads and displays the expense data for the current month in a pie chart format.
     * The data is fetched from the ExpensesController and only includes categories
     * with expenses greater than 0.01.
     * The title of the pie chart is set to the name of the current month.
     */
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
