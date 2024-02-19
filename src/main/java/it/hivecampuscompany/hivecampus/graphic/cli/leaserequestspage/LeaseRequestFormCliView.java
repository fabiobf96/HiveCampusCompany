package it.hivecampuscompany.hivecampus.graphic.cli.leaserequestspage;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class LeaseRequestFormCliView {

    List<String> months = Arrays.asList(
            "Gennaio", "Febbraio", "Marzo", "Aprile", "Maggio", "Giugno",
            "Luglio", "Agosto", "Settembre", "Ottobre", "Novembre", "Dicembre"
    );
    List<String> typesPermanence = Arrays.asList("6 mesi", "12 mesi", "24 mesi", "36 mesi");

    public void displayLeaseRequestForm() {
        System.out.println("_____________________________________________________________");
        System.out.println("LEASE REQUEST FORM");
        System.out.println("Tipo di permanenza:");
        for (String type : typesPermanence) {
            System.out.println(typesPermanence.indexOf(type) + 1 + ") " + type);
        }
        System.out.println("Inizio permanenza:");
        for (String month : months) {
            System.out.println(months.indexOf(month) + 1 + ". " + month);
        }
    }

    public String getUserInput(String prompt) {
        Scanner scanner = new Scanner(System.in);
        System.out.print(prompt);
        return scanner.nextLine();
    }

    public void displayMessage(String message) {
        System.out.println(message);
    }
}
