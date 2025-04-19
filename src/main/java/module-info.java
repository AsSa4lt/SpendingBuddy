module com.rostyslavliapkin.spendingbuddy {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.datatype.jsr310;
    requires com.fasterxml.jackson.databind;


    opens com.rostyslavliapkin.spendingbuddy to javafx.fxml;
    exports com.rostyslavliapkin.spendingbuddy;
    opens com.rostyslavliapkin.spendingbuddy.utils to com.fasterxml.jackson.databind;
    opens com.rostyslavliapkin.spendingbuddy.serializable to com.fasterxml.jackson.databind;
}