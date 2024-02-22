package it.hivecampuscompany.hivecampus.graphic.cli.ownerhomepage;


import it.hivecampuscompany.hivecampus.graphic.cli.managerequestowner.ManageLeaseOwnerCliController;
import it.hivecampuscompany.hivecampus.logic.bean.SessionBean;
import it.hivecampuscompany.hivecampus.logic.control.SessionManager;

public class OwnerHomeCliController {
    private final SessionBean sessionBean;
    private final OwnerHomeCliView view;
    public OwnerHomeCliController(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
        view = new OwnerHomeCliView();
        view.displayWelcomeMessage("OWNER HOME PAGE");
        handleUserChoice();
    }

    public void handleUserChoice() {
        int choice = view.getUserChoice();
        switch (choice) {
            case 2:
                new ManageLeaseOwnerCliController(sessionBean);
                break;
            case 5:
                SessionManager sessionManager = SessionManager.getInstance();
                sessionManager.closeSession(sessionBean);
                System.exit(0);
                break;
            default:
                view.displayMessage("Not Implemented YeT");
                handleUserChoice();
        }
    }
}
