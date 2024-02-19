package it.hivecampuscompany.hivecampus.graphic.cli.tenanthomepage;

import it.hivecampuscompany.hivecampus.graphic.cli.roomsearchpage.RoomSearchCliController;
import it.hivecampuscompany.hivecampus.logic.bean.SessionBean;
import it.hivecampuscompany.hivecampus.logic.exception.InvalidSessionException;

public class TenantHomeCliController {

    private final SessionBean sessionBean;
    private final TenantHomeCliView view;
    public TenantHomeCliController(SessionBean sessionBean) throws InvalidSessionException {
        this.sessionBean = sessionBean;
        this.view = new TenantHomeCliView();
        view.displayTenantHomeMessage();
        handleUserChoice();
    }

    public void handleUserChoice() throws InvalidSessionException {
        int choice = view.getUserChoice();
        switch (choice) {
            case 1:
                new RoomSearchCliController(sessionBean);
                RoomSearchCliController controller =  new RoomSearchCliController(sessionBean);
                controller.searchRooms();
                break;
            case 2:
                System.out.println("manage requests()");
                break;
            case 3:
                System.out.println("manage contract()");
                break;
            case 4:
                System.out.println("manage rent rates()");
                break;
            case 5:
                System.exit(0);
                break;
            default:
                view.displayMessage("\nInvalid choice.\n");
                handleUserChoice();
        }
    }
}
