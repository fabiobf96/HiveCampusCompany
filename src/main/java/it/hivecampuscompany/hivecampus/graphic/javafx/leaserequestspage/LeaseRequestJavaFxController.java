package it.hivecampuscompany.hivecampus.graphic.javafx.leaserequestspage;


import it.hivecampuscompany.hivecampus.logic.bean.SessionBean;
import it.hivecampuscompany.hivecampus.logic.control.RoomLeaseRequestManager;
import it.hivecampuscompany.hivecampus.logic.exception.InvalidSessionException;

public class LeaseRequestJavaFxController {
    private RoomLeaseRequestManager roomLeaseRequestManager;
    private SessionBean sessionBean;

    public LeaseRequestJavaFxController() throws InvalidSessionException {
        // Default constructor required by FXML
        this.roomLeaseRequestManager = new RoomLeaseRequestManager(sessionBean);
    }
    public LeaseRequestJavaFxController(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }
}
