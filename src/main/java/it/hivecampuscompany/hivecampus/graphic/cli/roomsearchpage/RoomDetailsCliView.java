package it.hivecampuscompany.hivecampus.graphic.cli.roomsearchpage;

import java.util.List;
import java.util.Scanner;

public class RoomDetailsCliView {
    public void displayRoomDetails(String title, List<String> houseFeatures, List<String> roomFeatures, List<String> ownerDetails, String availability, String distanceFromUniversity) {
        System.out.println("_____________________________________________________________");
        System.out.println(title);
        System.out.println("Caratteristiche della casa:");
        for (String feature : houseFeatures) {
            System.out.println(feature);
        }
        System.out.println("Caratteristiche della stanza:");
        for (String feature : roomFeatures) {
            System.out.println(feature);
        }
        System.out.println("Dati del proprietario:");
        for (String detail : ownerDetails) {
            System.out.println(detail);
        }
        System.out.println(availability);
        System.out.println(distanceFromUniversity);
        System.out.println("_____________________________________________________________");
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
