package it.hivecampuscompany.hivecampus.graphic.cli.tenanthomepage;

import java.util.InputMismatchException;
import java.util.Scanner;

public class TenantHomeCliView {

    public void displayTenantHomeMessage() {
        System.out.println("TENANT HOME PAGE");
    }

    public int getUserChoice() {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.println("1. Ricerca Camera");
            System.out.println("2. Gestione Richieste");
            System.out.println("3. Gestione Contratto");
            System.out.println("4. Gestione Rate di Affitto");
            System.out.println("5. Exit");
            System.out.print("Scelta: ");
            return scanner.nextInt();
        } catch (InputMismatchException e){
            System.out.println("Input non valido");
            scanner.nextLine();
            return getUserChoice();
        }
    }

    public void displayMessage(String message) {
        System.out.println(message);
    }

}
