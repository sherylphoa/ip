package sasa;

import sasa.commands.Command;
import sasa.exception.SasaException;

/**
 * Main class for the sasa.Sasa chatbot
 */
public class Sasa {
    private final Ui ui;
    private final Storage storage;
    private TaskList tasks;

    public Sasa(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            tasks = new TaskList(storage.load());
        } catch (SasaException e) {
            ui.showLoadingError();
            tasks = new TaskList();
        }
    }

    /**
     * Main entry point for the application.
     *
     * @param args sasa.commands.Command line arguments.
     */
    public static void main(String[] args) {
        // 2. Start the program as an instance
        new Sasa("data/sasa.txt").run();
    }

    public void run() {
        ui.showWelcome();
        boolean isExit = false;

        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                ui.showLine(); // show the divider line ("_______")
                Command c = Parser.parse(fullCommand);
                c.execute(tasks, ui, storage);
                isExit = c.isExit();
            } catch (SasaException e) {
                ui.showError(e.getMessage());
            } finally {
                ui.showLine();
            }
        }

        ui.showLine();
        ui.showMessage(" Bye. Come back when you need me again!");
        ui.showLine();
    }
}