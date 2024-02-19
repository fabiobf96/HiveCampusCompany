package it.hivecampuscompany.hivecampus.graphic.cli.leaserequestspage;

import it.hivecampuscompany.hivecampus.logic.bean.AccountBean;
import it.hivecampuscompany.hivecampus.logic.bean.RoomBean;
import it.hivecampuscompany.hivecampus.logic.bean.SessionBean;
import it.hivecampuscompany.hivecampus.logic.control.RoomLeaseRequestManager;

import java.util.Arrays;
import java.util.List;

public class LeaseRequestCliController {

    private SessionBean sessionBean;
    private final LeaseRequestFormCliView view;

    public LeaseRequestCliController(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
        this.view = new LeaseRequestFormCliView();
    }

    public void handleRoomLeaseRequest(RoomLeaseRequestManager roomLeaseRequestManager, RoomBean roomBean, AccountBean ownerAccountBean) {
        view.displayLeaseRequestForm();
        String typePermanence = convertTypePermanence(view.getUserInput("Inserisci il tipo di permaneza: "));
        String startPermanence = convertStartPermanence(view.getUserInput("Inserisci il mese di inizio permanenza: "));

        String choice = view.getUserInput("Inviare la richiesta di affitto? (si/no): ");
        if (choice.equals("si")) {
            //roomLeaseRequestManager . sendLeaseRequest(roomBean.getIdRoom(), ownerAccountBean.getEmail(), typePermanence, startPermanence)
            System.out.println("Riepilogo richiesta: " + roomBean.getIdRoom() + " " + ownerAccountBean.getEmail() + " " + typePermanence + " " + startPermanence);
            view.displayMessage("Richiesta di affitto inviata con successo.");

        }
    }

    private String convertTypePermanence(String userInput) {
        List<String> typesPermanence = Arrays.asList("6 mesi", "12 mesi", "24 mesi", "36 mesi");
        for (int i = 0; i < typesPermanence.size(); i++) {
            if (userInput.equals(String.valueOf(i + 1))) {
                return typesPermanence.get(i);
            }
        }
        view.displayMessage("Tipo permanenza non valido.");
        return convertTypePermanence(view.getUserInput("Inserisci il tipo di permaneza: "));
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
        view.displayMessage("Mese di inizio permanenza non valido.");
        return convertStartPermanence(view.getUserInput("Inserisci il mese di inizio permanenza: "));
    }
}
