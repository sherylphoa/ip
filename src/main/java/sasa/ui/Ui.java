package sasa.ui;

import java.util.Scanner;

/**
 * Handles all interactions with the user.
 * Displays messages, errors and reading user input from the console.
 */
public class Ui {
    private static final String HORIZONTAL_LINE = "____________________________________________________________";
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
    public String showWelcome() {
        return " Hello! I'm Sasa\n Your wish is my command";
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

    /**
     * Displays the list of tasks found during a search.
     *
     * @param matchingTasks The list of tasks that matched the search criteria.
     */
    public void showMatchingTasks(java.util.ArrayList<sasa.tasks.Task> matchingTasks) {
        if (matchingTasks.isEmpty()) {
            System.out.println(" There are no matching tasks in your list.");
        } else {
            System.out.println(" Here are the matching tasks in your list:");
            for (int i = 0; i < matchingTasks.size(); i++) {
                System.out.println(" " + (i + 1) + "." + matchingTasks.get(i));
            }
        }
    }
}
