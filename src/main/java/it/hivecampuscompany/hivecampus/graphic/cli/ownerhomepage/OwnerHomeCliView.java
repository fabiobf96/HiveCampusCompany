package it.hivecampuscompany.hivecampus.graphic.cli.ownerhomepage;

import it.hivecampuscompany.hivecampus.graphic.cli.CliView;

import java.util.InputMismatchException;
import java.util.Scanner;

public class OwnerHomeCliView extends CliView {
    public int getUserChoice() {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.println("1. Manage Ads");
            System.out.println("2. Manage Requests");
            System.out.println("3. Manage Contracts");
            System.out.println("4. Manage Rent Rates");
            System.out.println("5. Exit");
            System.out.print("Choice: ");
            return scanner.nextInt();
        } catch (InputMismatchException e){
            System.out.println("Invalid Input");
            scanner.nextLine();
            return getUserChoice();
        }
    }

}

