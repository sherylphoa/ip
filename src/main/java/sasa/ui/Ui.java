package sasa.ui;

import java.util.Scanner;

/**
 * Handles all interactions with the user.
 * Displays messages, errors and reading user input from the console.
 */
public class Ui {
    private final String HORIZONTAL_LINE = "____________________________________________________________";
    private final Scanner scanner;

    /**
     * Constructs an Ui object and initializes the scanner for user input.
     */
    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Prints a horizontal line to the console for visual formatting.
     */
    public void showLine() {
        System.out.println(HORIZONTAL_LINE);
    }

    /**
     * Displays the welcome message and the logo/name of the chatbot.
     */
    public void showWelcome() {
        showLine();
        System.out.println(" Hello! I'm sasa.Sasa\n Your wish is my command");
        showLine();
    }

    /**
     * Reads the next line of input entered by the user.
     *
     * @return The raw string entered by the user.
     */
    public String readCommand() {
        return scanner.nextLine();
    }

    /**
     * Displays a formatted error message to the user.
     *
     * @param message The error details to be displayed.
     */
    public void showError(String message) {
        System.out.println(" OOPSIE! " + message);
    }

    /**
     * Displays a specific error message when the storage file fails to load.
     */
    public void showLoadingError() {
        System.out.println(" Error loading data. Starting with an empty list.");
    }

    /**
     * Displays a general message to the user.
     *
     * @param message The string content to be printed.
     */
    public void showMessage(String message) {
        System.out.println(" " + message);
    }
}
