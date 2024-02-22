package it.hivecampuscompany.hivecampus.graphic.cli.managerequestowner;

import it.hivecampuscompany.hivecampus.graphic.cli.CliView;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ManagerLeaseRequestOwnerCliView extends CliView {

    @Override
    public int getUserChoice() {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.println("1. Accept request");
            System.out.println("2. Reject request");
            System.out.println("3. Go back ");
            System.out.print("Choice: ");
            return scanner.nextInt();
        } catch (InputMismatchException e){
            System.out.println("Invalid Input");
            scanner.nextLine();
            return getUserChoice();
        }
    }
}
