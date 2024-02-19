package it.hivecampuscompany.hivecampus.graphic.cli.roomsearchpage;

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
}
