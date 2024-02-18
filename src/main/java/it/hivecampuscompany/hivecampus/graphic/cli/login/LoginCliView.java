package it.hivecampuscompany.hivecampus.graphic.cli.login;

import java.util.InputMismatchException;
import java.util.Scanner;

public class LoginCliView {
    public void displayWelcomeMessage() {
        System.out.println("Welcome in Hive Campus!");
    }

    public int getUserChoice() {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.println("1. Logon");
            System.out.println("2. Create new account");
            System.out.println("3. Exit");
            System.out.print("Choice: ");
            return scanner.nextInt();
        } catch (InputMismatchException e){
            System.out.println("Invalid input.");
            scanner.nextLine();
            return getUserChoice();
        }
    }

    public int getAccountType() {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.println("1. Owner");
            System.out.println("2. Tenant");
            System.out.print("Choice: ");
            return scanner.nextInt();
        } catch (InputMismatchException e){
            System.out.println("Invalid input.");
            scanner.nextLine();
            return getAccountType();
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
