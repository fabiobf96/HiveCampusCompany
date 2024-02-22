package it.hivecampuscompany.hivecampus.graphic.cli;

import java.util.Scanner;

public abstract class CliView {
    public abstract int getUserChoice();

    public void displayWelcomeMessage(String title) {
        int totalWidth = 80;
        int padding = (totalWidth - title.length()) / 2; // Calcola lo spazio prima del titolo per centrarlo
        String formattedTitle = " ".repeat(padding) + title;
        String underline = "_".repeat(totalWidth);

        System.out.println(formattedTitle);
        System.out.println(underline);
        System.out.println("\n\n");
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
