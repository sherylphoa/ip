package sasa;

import java.util.Scanner;

public class Ui {
    private final String HORIZONTAL_LINE = "____________________________________________________________";
    private final Scanner scanner;

    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    public void showLine() {
        System.out.println(HORIZONTAL_LINE);
    }

    public void showWelcome() {
        showLine();
        System.out.println(" Hello! I'm sasa.Sasa\n Your wish is my command");
        showLine();
    }

    public String readCommand() {
        return scanner.nextLine();
    }

    public void showError(String message) {
        System.out.println(" OOPSIE! " + message);
    }

    public void showLoadingError() {
        System.out.println(" Error loading data. Starting with an empty list.");
    }

    public void showMessage(String message) {
        System.out.println(" " + message);
    }
}
