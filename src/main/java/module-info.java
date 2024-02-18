module it.hivecampuscompany.hivecampus {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.opencsv;


    opens it.hivecampuscompany.hivecampus to javafx.fxml;
    exports it.hivecampuscompany.hivecampus;
}