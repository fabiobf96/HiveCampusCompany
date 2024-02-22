package it.hivecampuscompany.hivecampus.graphic.cli.managerequestowner;

import it.hivecampuscompany.hivecampus.graphic.cli.CliView;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ManageLeaseOwnerCliView extends CliView {
    @Override
    public int getUserChoice() {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.println("1. Manage Lease Request");
            System.out.println("2. Manage Lease");
            System.out.println("3. Go back ");
            System.out.print("Choice: ");
            return scanner.nextInt();
        } catch (InputMismatchException e){
            System.out.println("Invalid Input");
            scanner.nextLine();
            return getUserChoice();
        }
    }

    public String getLeasePath() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the file path: ");
        String inputPath = scanner.nextLine();
        Path path = Paths.get(inputPath);

        if (!Files.exists(path) && !Files.isDirectory(path)) { // Verifica che il percorso esista e non sia una directory
            System.out.println("The path entered is incorrect");
            getLeasePath();
        }
        return path.toString();
    }
}
