package it.hivecampuscompany.hivecampus.graphic.cli.leaserequestspage;

import it.hivecampuscompany.hivecampus.logic.bean.LeaseRequestBean;
import it.hivecampuscompany.hivecampus.logic.bean.RoomBean;
import it.hivecampuscompany.hivecampus.logic.bean.SessionBean;
import it.hivecampuscompany.hivecampus.logic.control.RoomLeaseRequestManager;
import it.hivecampuscompany.hivecampus.logic.exception.EmptyListException;
import it.hivecampuscompany.hivecampus.logic.exception.InvalidSessionException;

import java.util.Arrays;
import java.util.List;

public class LeaseRequestCliController {

    private final SessionBean sessionBean;
    private final RoomLeaseRequestManager manager;
    private final LeaseRequestFormCliView formView;


    public LeaseRequestCliController(SessionBean sessionBean, RoomLeaseRequestManager manager) {
        this.sessionBean = sessionBean;
        this.manager = manager;
        this.formView = new LeaseRequestFormCliView();
    }

    public void handleRoomLeaseRequest(RoomBean roomBean) throws InvalidSessionException {
        formView.displayLeaseRequestForm();
        String typePermanence = convertTypePermanence(formView.getUserInput("Inserisci il tipo di permaneza: "));
        String startPermanence = convertStartPermanence(formView.getUserInput("Inserisci il mese di inizio permanenza: "));

        String choice = formView.getUserInput("Inviare la richiesta di affitto? (si/no): ");
        if (choice.equals("si")) {

            LeaseRequestBean leaseRequestBean = new LeaseRequestBean();
            leaseRequestBean.setRoomBean(roomBean);
            leaseRequestBean.setTenant(null);
            leaseRequestBean.setTypePermanence(typePermanence);
            leaseRequestBean.setStartPermanence(startPermanence);
            leaseRequestBean.setStatusRequest("elaborazione");

            manager.sendLeaseRequest(sessionBean, leaseRequestBean);
            formView.displayMessage("Richiesta di affitto inviata con successo.");
        }
    }

    private String convertTypePermanence(String userInput) {
        List<String> typesPermanence = Arrays.asList("6mesi", "12mesi", "24mesi", "36mesi");
        for (int i = 0; i < typesPermanence.size(); i++) {
            if (userInput.equals(String.valueOf(i + 1))) {
                return typesPermanence.get(i);
            }
        }
        formView.displayMessage("Tipo permanenza non valido.");
        return convertTypePermanence(formView.getUserInput("Inserisci il tipo di permaneza: "));
    }

    private String convertStartPermanence(String userInput) {
        List<String> months = Arrays.asList(
                "Gennaio", "Febbraio", "Marzo", "Aprile", "Maggio", "Giugno",
                "Luglio", "Agosto", "Settembre", "Ottobre", "Novembre", "Dicembre"
        );
        for (int i = 0; i < months.size(); i++) {
            if (userInput.equals(String.valueOf(i + 1))) {
                return months.get(i);
            }
        }
        formView.displayMessage("Mese di inizio permanenza non valido.");
        return convertStartPermanence(formView.getUserInput("Inserisci il mese di inizio permanenza: "));
    }
}