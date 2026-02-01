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

        while (true) {
            String input = ui.readCommand();
            if (input.equalsIgnoreCase("bye")) {
                break;
            }
            try {
                ui.showLine();
                if (input.equalsIgnoreCase("list")) {
                    tasks.listTasks(ui);
                } else if (input.startsWith("mark")) {
                    int index = Integer.parseInt(input.substring(5)) - 1;
                    tasks.markTask(index, ui);
                    storage.save(tasks.getTasks());
                } else if (input.startsWith("unmark")) {
                    int index = Integer.parseInt(input.substring(7)) - 1;
                    tasks.unmarkTask(index, ui);
                    storage.save(tasks.getTasks());
                } else if (input.startsWith("todo")) {
                    if (input.length() <= 5) {
                        throw new SasaException("Please include your task.");
                    }
                    tasks.addTask(new Todo(input.substring(5)), ui);
                    storage.save(tasks.getTasks());
                } else if (input.startsWith("deadline")) {
                    if (input.length() <= 9) {
                        throw new SasaException("Please include your task with the deadline.");
                    }
                    if (!input.contains(" /by ")) {
                        throw new SasaException("Deadlines must include ' /by '.");
                    }
                    String[] parts = input.substring(9).split(" /by ");
                    tasks.addTask(new Deadline(parts[0], parts[1]), ui);
                    storage.save(tasks.getTasks());
                } else if (input.startsWith("event")) {
                    if (input.length() <= 6) {
                        throw new SasaException("Please include your task with the duration.");
                    }
                    if (!input.contains(" /from ") || !input.contains(" /to ")) {
                        throw new SasaException("Events must include ' /from ' and ' /to '.");
                    }
                    String[] parts = input.substring(6).split(" /from ");
                    String[] timeParts = parts[1].split(" /to ");
                    tasks.addTask(new Event(parts[0], timeParts[0], timeParts[1]), ui);
                    storage.save(tasks.getTasks());
                } else if (input.startsWith("delete")) {
                    int index = Integer.parseInt(input.substring(7)) - 1;
                    tasks.deleteTask(index, ui);
                    storage.save(tasks.getTasks());
                } else {
                    throw new SasaException("Sorry, I do not know what that means :(");
                }
            } catch (SasaException e) {
                ui.showError(e.getMessage());
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException | StringIndexOutOfBoundsException e) {
                ui.showError("Please provide a valid task number.");
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