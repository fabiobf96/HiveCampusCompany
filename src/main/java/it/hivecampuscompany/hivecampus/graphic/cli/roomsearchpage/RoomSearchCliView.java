package it.hivecampuscompany.hivecampus.graphic.cli.roomsearchpage;

import java.util.InputMismatchException;
import java.util.Scanner;

public class RoomSearchCliView {

    public void displayRoomSearchMessage() {
        System.out.println("ROOM SEARCH");
    }

    public String getUserInput(String prompt) {
        Scanner scanner = new Scanner(System.in);
        System.out.print(prompt);
        return scanner.nextLine();
    }

    public void displayMessage(String message) {
        System.out.println(message);
    }

    /*
    public int getUserChoice() {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.println("1. See room details");
            System.out.println("2. Go back");
            System.out.println("3. Exit");
            System.out.print("Choice: ");
            return scanner.nextInt();
        } catch (InputMismatchException e){
            System.out.println("Invalid input.");
            scanner.nextLine();
            return getUserChoice();
        }
    }

     */
}
