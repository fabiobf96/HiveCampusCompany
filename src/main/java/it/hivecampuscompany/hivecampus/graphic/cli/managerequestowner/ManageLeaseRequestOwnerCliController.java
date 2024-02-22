package it.hivecampuscompany.hivecampus.graphic.cli.managerequestowner;

import it.hivecampuscompany.hivecampus.graphic.cli.ownerhomepage.OwnerHomeCliController;
import it.hivecampuscompany.hivecampus.logic.bean.LeaseRequestBean;
import it.hivecampuscompany.hivecampus.logic.bean.PreviewRoomBean;
import it.hivecampuscompany.hivecampus.logic.bean.SessionBean;
import it.hivecampuscompany.hivecampus.logic.control.LeaseManager;
import it.hivecampuscompany.hivecampus.logic.exception.EmptyFieldsException;
import it.hivecampuscompany.hivecampus.logic.exception.EmptyListException;
import it.hivecampuscompany.hivecampus.logic.exception.InvalidSessionException;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ManageLeaseRequestOwnerCliController {
    private final SessionBean sessionBean;
    private List<PreviewRoomBean> previewRoomBeans;
    private final ManagerLeaseRequestOwnerCliView view;
    private LeaseManager manager;
    private static final Logger LOGGER = Logger.getLogger(ManageLeaseRequestOwnerCliController.class.getName());

    ManageLeaseRequestOwnerCliController(SessionBean sessionBean) {
        this.view = new ManagerLeaseRequestOwnerCliView();
        this.sessionBean = sessionBean;
        try {
            this.manager = new LeaseManager(sessionBean);
            this.previewRoomBeans = manager.fetchLeaseRequest(sessionBean);
        } catch (InvalidSessionException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            System.exit(1);
        } catch (EmptyListException | EmptyFieldsException e) {
            view.displayMessage(e.getMessage());
            new OwnerHomeCliController(sessionBean);
        }

        view.displayWelcomeMessage("MANAGE LEASE REQUEST");
        homeChoice();
    }

    public void homeChoice() {
        List<PreviewRoomBean> previewRoomBeanList = new ArrayList<>();
        for (PreviewRoomBean previewRoomBean : previewRoomBeans) {
            List<LeaseRequestBean> leaseRequestBeans = previewRoomBean.getLeaseRequestBeans();
            if (leaseRequestBeans != null && !leaseRequestBeans.isEmpty()) {
                LeaseRequestBean firstRequest = leaseRequestBeans.getFirst();
                if (!"accettata".equalsIgnoreCase(firstRequest.getStatusRequest())) {
                    previewRoomBeanList.add(previewRoomBean);
                }
            }
        }

        if (previewRoomBeanList.isEmpty()) {
            view.displayMessage("There are any lease request for your rooms");
            new ManageLeaseOwnerCliController(sessionBean);
            return; // Aggiunta di return per evitare l'esecuzione del codice seguente se non ci sono richieste
        }

        int i = 0;
        int homeChoice = -1;

        for (PreviewRoomBean previewRoomBean : previewRoomBeanList) {
            view.displayMessage(i + ". " + previewRoomBean.toString());
            i++;
        }

        view.displayMessage(i + ". Go back");
        while (homeChoice < 0 || homeChoice > i) {
            homeChoice = Integer.parseInt(view.getUserInput("choice: "));
        }
        if (homeChoice == i) {
            new ManageLeaseOwnerCliController(sessionBean);
            return; // Aggiunta di return per evitare l'esecuzione del codice seguente se l'utente sceglie di tornare indietro
        }
        tenantChoice(previewRoomBeanList.get(homeChoice)); // Passaggio dell'elemento selezionato alla prossima fase
    }

    private void tenantChoice(PreviewRoomBean previewRoomBean) {
        List<LeaseRequestBean> leaseRequestBeans = previewRoomBean.getLeaseRequestBeans();
        int i = 0;
        int choice = -1;
        for (LeaseRequestBean leaseRequestBean : leaseRequestBeans) {
            view.displayMessage(i + ". " + leaseRequestBean.toString());
            i++;
        }

        view.displayMessage(i + ". Go back");
        while (choice < 0 || choice > i) {
            choice = Integer.parseInt(view.getUserInput("choice: "));
        }
        if (choice == i) {
            homeChoice(); // Torna alla selezione della stanza se l'utente sceglie di tornare indietro
            return; // Aggiunta di return per evitare l'esecuzione del codice seguente
        }

        manageLeaseRequest(previewRoomBean, choice); // Passaggio del bean specifico e della scelta al metodo
    }

    private void manageLeaseRequest(PreviewRoomBean previewRoomBean, int leaseChoice) {
        // Modifica: semplificazione della logica di scelta, presupponendo che view.getUserChoice() restituisca un intero
        switch (view.getUserChoice()) {
            case 1 -> acceptLeaseRequest(previewRoomBean, leaseChoice);
            case 2 -> rejectLeaseRequest(previewRoomBean, leaseChoice);
            case 3 -> tenantChoice(previewRoomBean); // Torna alla scelta del tenant senza uscire
            default -> {
                view.displayMessage("Value not valid");
                manageLeaseRequest(previewRoomBean, leaseChoice); // Richiama se stesso per una nuova selezione
            }
        }
    }

    private void rejectLeaseRequest(PreviewRoomBean previewRoomBean, int leaseChoice) {
        LeaseRequestBean leaseRequestBean = previewRoomBean.getLeaseRequestBeans().get(leaseChoice);
        leaseRequestBean.setStatusRequest("rifiutata");
        try {
            previewRoomBeans = manager.manageLeaseRequest(sessionBean, leaseRequestBean);
        } catch (InvalidSessionException e) {
            view.displayMessage(e.getMessage());
            System.exit(1);
        }
        homeChoice();
    }

    private void acceptLeaseRequest(PreviewRoomBean previewRoomBean, int leaseChoice) {
        LeaseRequestBean leaseRequestBean = previewRoomBean.getLeaseRequestBeans().get(leaseChoice);
        leaseRequestBean.setStatusRequest("accettata");
        try {
            previewRoomBeans = manager.manageLeaseRequest(sessionBean, leaseRequestBean);
        } catch (InvalidSessionException e) {
            view.displayMessage(e.getMessage());
            System.exit(1);
        }
        homeChoice();
    }
}
