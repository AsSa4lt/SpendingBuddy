module com.rostyslavliapkin.spendingbuddy {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.rostyslavliapkin.spendingbuddy to javafx.fxml;
    exports com.rostyslavliapkin.spendingbuddy;
}