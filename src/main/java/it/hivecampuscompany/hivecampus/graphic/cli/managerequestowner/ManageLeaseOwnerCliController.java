package it.hivecampuscompany.hivecampus.graphic.cli.managerequestowner;

import it.hivecampuscompany.hivecampus.graphic.cli.ownerhomepage.OwnerHomeCliController;
import it.hivecampuscompany.hivecampus.logic.bean.LeaseBean;
import it.hivecampuscompany.hivecampus.logic.bean.PreviewRoomBean;
import it.hivecampuscompany.hivecampus.logic.bean.SessionBean;
import it.hivecampuscompany.hivecampus.logic.control.LeaseManager;
import it.hivecampuscompany.hivecampus.logic.exception.EmptyFieldsException;
import it.hivecampuscompany.hivecampus.logic.exception.EmptyListException;
import it.hivecampuscompany.hivecampus.logic.exception.InvalidSessionException;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ManageLeaseOwnerCliController {
    private LeaseManager manager;
    private ManageLeaseOwnerCliView view;
    private List<PreviewRoomBean> previewRoomBeans;
    private SessionBean sessionBean;

    private static final Logger LOGGER = Logger.getLogger(ManageLeaseOwnerCliController.class.getName());

    public ManageLeaseOwnerCliController(SessionBean sessionBean) {
        try {
            this.sessionBean = sessionBean;
            view = new ManageLeaseOwnerCliView();
            manager = new LeaseManager(sessionBean);
            previewRoomBeans = manager.fetchLeaseRequest(sessionBean);

        } catch (InvalidSessionException e) {
            view.displayMessage(e.getMessage());
            System.exit(1);
        } catch (EmptyListException | EmptyFieldsException e) {
            view.displayMessage(e.getMessage());
            new OwnerHomeCliController(sessionBean);
        }
        view.displayWelcomeMessage("MANAGE LEASE");
        ownerChoice();
    }

    public void ownerChoice() {
        switch (view.getUserChoice()) {
            case 1 -> new ManageLeaseRequestOwnerCliController(sessionBean);
            case 2 -> leaseRoom();
            case 3 -> new OwnerHomeCliController(sessionBean);
            default -> {
                view.displayMessage("invalid choice");
                ownerChoice();
            }
        }
    }


    public void leaseRoom() {
        List<PreviewRoomBean> previewRoomBeanList = new ArrayList<>();
        for (PreviewRoomBean previewRoomBean : previewRoomBeans) {
            if (previewRoomBean.getLeaseRequestBeans().getFirst().getStatusRequest().equals("accettata")) {
                previewRoomBeanList.add(previewRoomBean);
            }
        }
        if (previewRoomBeanList.isEmpty()) {
            view.displayMessage("There are any lease to manage for your rooms");
            new OwnerHomeCliController(sessionBean);
        }
        int i = 0;
        int choice = -1;

        for (PreviewRoomBean previewRoomBean : previewRoomBeanList) {
            view.displayMessage(i + ". " + previewRoomBean.toString() + previewRoomBean.getLeaseRequestBeans().getFirst().toString());
            i++;
        }

        view.displayMessage(i + "." + " Go back");
        while (choice < 0 || choice > i) {
            choice = Integer.parseInt(view.getUserInput("Choice: "));
        }
        if (choice == i) {
            ownerChoice();
        }
        loadContract(previewRoomBeanList.get(choice));
    }

    private void loadContract(PreviewRoomBean previewRoomBean) {
        LeaseBean leaseBean = new LeaseBean(previewRoomBean, previewRoomBean.getLeaseRequestBeans().getFirst().getTenant(), false);
        leaseBean.setFile(Paths.get(view.getLeasePath()).toFile());
        try {
            manager.createLease(sessionBean, leaseBean);
        } catch (InvalidSessionException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            System.exit(1);
        } catch (EmptyListException e) {
            //Questa eccezzione la devo gestire ma non mi serve
        }
    }
}
