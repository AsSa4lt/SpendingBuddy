module com.rostyslavliapkin.spendingbuddy {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.rostyslavliapkin.spendingbuddy to javafx.fxml;
    exports com.rostyslavliapkin.spendingbuddy;
}