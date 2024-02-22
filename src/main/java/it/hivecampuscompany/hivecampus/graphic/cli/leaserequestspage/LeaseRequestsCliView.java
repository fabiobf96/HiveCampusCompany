package it.hivecampuscompany.hivecampus.graphic.cli.leaserequestspage;

import it.hivecampuscompany.hivecampus.logic.bean.LeaseRequestBean;

import java.util.Scanner;

public class LeaseRequestsCliView {
    public void displayRoomSearchMessage() {
        System.out.println("MANAGE LEASE REQUESTS");
    }

    public String getUserInput(String prompt) {
        Scanner scanner = new Scanner(System.in);
        System.out.print(prompt);
        return scanner.nextLine();
    }

    public void displayMessage(String message) {
        System.out.println(message);
    }

    public void displayLeaseRequest(LeaseRequestBean leaseRequestBean) {
        System.out.println("Richiesta di affitto:");
        System.out.println("ID: " + leaseRequestBean.getId());
        System.out.println("Stanza: " + leaseRequestBean.getRoomBean().getTypeRoom());
        System.out.println("Tipo di permanenza: " + leaseRequestBean.getTypePermanence());
        System.out.println("Inizio permanenza: " + leaseRequestBean.getStartPermanence());
        System.out.println("Stato: " + leaseRequestBean.getStatusRequest());
    }
}
