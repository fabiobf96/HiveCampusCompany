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


}

