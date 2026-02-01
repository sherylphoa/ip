/**
 * Main class for the Sasa chatbot
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
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        // 2. Start the program as an instance
        new Sasa("data/sasa.txt").run();
    }

    public void run() {
        ui.showWelcome();
        boolean isExit = false;

        while (!isExit) {
            String input = ui.readCommand();
            String[] components = Parser.parseInput(input);
            String command = components[0].toLowerCase();
            String arg = components.length > 1 ? components[1] : "";
            if (command.equals("bye")) {
                isExit = true;
                continue;
            }
            try {
                ui.showLine();
                switch(command) {
                    case "list":
                        tasks.listTasks(ui);
                        break;

                    case "mark":
                        tasks.markTask(Parser.parseIndex(arg), ui);
                        storage.save(tasks.getTasks());
                        break;

                    case "unmark":
                        tasks.unmarkTask(Parser.parseIndex(arg), ui);
                        storage.save(tasks.getTasks());
                        break;

                    case "todo":
                        if (arg.isEmpty()) {
                            throw new SasaException("Please include your task.");
                        }
                        tasks.addTask(new Todo(arg), ui);
                        storage.save(tasks.getTasks());
                        break;

                    case "deadline":
                        String[] dParts = Parser.parseDeadline(arg);
                        tasks.addTask(new Deadline(dParts[0], dParts[1]), ui);
                        storage.save(tasks.getTasks());
                        break;

                    case "event":
                        String[] eParts = Parser.parseEvent(arg);
                        tasks.addTask(new Event(eParts[0], eParts[1], eParts[2]), ui);
                        storage.save(tasks.getTasks());
                        break;

                    case "delete":
                        tasks.deleteTask(Parser.parseIndex(arg), ui);
                        storage.save(tasks.getTasks());
                        break;

                    default:
                        throw new SasaException("Sorry, I do not know what that means :(");
                }
            } catch (SasaException e) {
                ui.showError(e.getMessage());
            } catch (java.time.format.DateTimeParseException e) {
                ui.showError("I can't understand that date. Use the format: d/M/yyyy HHmm. \n Example format: 31/1/2025 2359");
            } finally {
                ui.showLine();
            }
        }

        ui.showLine();
        ui.showMessage(" Bye. Come back when you need me again!");
        ui.showLine();
    }
}