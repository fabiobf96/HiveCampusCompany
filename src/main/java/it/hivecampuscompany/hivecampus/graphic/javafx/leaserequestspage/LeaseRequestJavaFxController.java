package it.hivecampuscompany.hivecampus.graphic.javafx.leaserequestspage;


import it.hivecampuscompany.hivecampus.logic.bean.RoomBean;
import it.hivecampuscompany.hivecampus.logic.bean.SessionBean;
import it.hivecampuscompany.hivecampus.logic.control.RoomLeaseRequestManager;
import it.hivecampuscompany.hivecampus.logic.exception.InvalidSessionException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class LeaseRequestJavaFxController {
    private RoomLeaseRequestManager roomLeaseRequestManager;
    private SessionBean sessionBean;

    private RoomBean roomBean;



    @FXML
    private VBox vboxStart;

    @FXML
    private HBox hboxType ;

    public LeaseRequestJavaFxController() {
        // Default constructor
    }

    public void initialize(SessionBean sessionBean, RoomLeaseRequestManager roomLeaseRequestManager,  RoomBean roomBean) {
        this.sessionBean = sessionBean;
        this.roomLeaseRequestManager = roomLeaseRequestManager;
        this.roomBean = roomBean;
    }
    /*
    @FXML
    public void handleStartPermancenceClick() {
        // Manage the start permanence button click event
        vboxStart.getChildren().forEach(hbox -> {
            ((HBox) hbox).getChildren().forEach(button -> {
                ((Button) button).setOnAction(event -> handleMonthButtonClick((Button) button));
            });
        });
        System.out.println("Start permanence button clicked");
    }

    private void handleMonthButtonClick(Button button) {

    }

    @FXML
    public void handleTypePermancenceClick() {
        // Manage the start permanence button click event
        hboxType.getChildren().forEach(button -> {
            button.setOnMouseClicked(event -> handleTypeButtonClick((Button) button));
        });
        System.out.println("Start permanence button clicked");
    }

    private void handleTypeButtonClick(Button button) {

    }


    @FXML

    public void handleSendRequestClick() {




        try {
            roomLeaseRequestManager.sendLeaseRequest();
        } catch (InvalidSessionException e) {
            e.printStackTrace();
        }
    }

     */


}

