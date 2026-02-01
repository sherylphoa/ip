package sasa.ui;

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
