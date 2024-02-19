module it.hivecampuscompany.hivecampus {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.opencsv;


    opens it.hivecampuscompany.hivecampus to javafx.fxml;
    opens it.hivecampuscompany.hivecampus.graphic.javafx.login to javafx.fxml;
    opens it.hivecampuscompany.hivecampus.graphic.javafx.signup to javafx.fxml;
    opens it.hivecampuscompany.hivecampus.graphic.javafx.ownerhomepage to javafx.fxml;
    opens it.hivecampuscompany.hivecampus.graphic.javafx.tenanthomepage to javafx.fxml;
    opens it.hivecampuscompany.hivecampus.graphic.javafx.roomsearchpage to javafx.fxml;

    exports it.hivecampuscompany.hivecampus.graphic.javafx.roomsearchpage;
    exports it.hivecampuscompany.hivecampus.graphic.javafx.leaserequestspage;
    exports it.hivecampuscompany.hivecampus;
    exports it.hivecampuscompany.hivecampus.graphic.javafx.login;
    exports it.hivecampuscompany.hivecampus.logic.control;
    exports it.hivecampuscompany.hivecampus.logic.bean;
    exports it.hivecampuscompany.hivecampus.logic.exception;
}