import java.util.ArrayList;

/**
 * Main class for the Sasa chatbot
 */
public class Sasa {
    private final Ui ui;
    private final Storage storage;
    private ArrayList<Task> tasks;

    public Sasa(String filePath) {
        this.ui = new Ui();
        this.storage = new Storage(filePath);
        try {
            this.tasks = storage.load();
        } catch (SasaException e) {
            ui.showLoadingError();
            this.tasks = new ArrayList<>();
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
                    ui.showMessage("Here are your tasks:");
                    for (int i = 0; i < tasks.size(); i++) {
                        ui.showMessage(" " + (i + 1) + "." + tasks.get(i));
                    }
                } else if (input.startsWith("mark")) {
                    int index = Integer.parseInt(input.substring(5)) - 1;
                    checkIndex(index, tasks);
                    tasks.get(index).markAsDone();
                    ui.showMessage(" Nice! This task is marked:");
                    ui.showMessage("   " + tasks.get(index));
                    storage.save(tasks);
                } else if (input.startsWith("unmark")) {
                    int index = Integer.parseInt(input.substring(7)) - 1;
                    checkIndex(index, tasks);
                    tasks.get(index).unmarkAsDone();
                    ui.showMessage(" OK, This task is unmarked:");
                    ui.showMessage("   " + tasks.get(index));
                    storage.save(tasks);
                } else if (input.startsWith("todo")) {
                    if (input.length() <= 5) {
                        throw new SasaException("Please include your task.");
                    }
                    tasks.add(new Todo(input.substring(5)));
                    addMessage(tasks);
                    storage.save(tasks);
                } else if (input.startsWith("deadline")) {
                    if (input.length() <= 9) {
                        throw new SasaException("Please include your task with the deadline.");
                    }
                    if (!input.contains(" /by ")) {
                        throw new SasaException("Deadlines must include ' /by '.");
                    }
                    String[] parts = input.substring(9).split(" /by ");
                    tasks.add(new Deadline(parts[0], parts[1]));
                    addMessage(tasks);
                    storage.save(tasks);
                } else if (input.startsWith("event")) {
                    if (input.length() <= 6) {
                        throw new SasaException("Please include your task with the duration.");
                    }
                    if (!input.contains(" /from ") || !input.contains(" /to ")) {
                        throw new SasaException("Events must include ' /from ' and ' /to '.");
                    }
                    String[] parts = input.substring(6).split(" /from ");
                    String[] timeParts = parts[1].split(" /to ");
                    tasks.add(new Event(parts[0], timeParts[0], timeParts[1]));
                    addMessage(tasks);
                    storage.save(tasks);
                } else if (input.startsWith("delete")) {
                    int index = Integer.parseInt(input.substring(7)) - 1;
                    checkIndex(index, tasks);
                    Task removed = tasks.remove(index);
                    ui.showMessage(" I've removed this task:\n   " + removed);
                    ui.showMessage(" Now you have " + tasks.size() + " tasks in the list.");
                    storage.save(tasks);
                } else {
                    throw new SasaException("Sorry, I do not know what that means :(");
                }
                ui.showLine();
            } catch (SasaException e) {
                ui.showError(e.getMessage());
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException | StringIndexOutOfBoundsException e) {
                ui.showError("Please provide a valid task number.");
            } catch (java.time.format.DateTimeParseException e) {
                ui.showError("I can't understand that date. Use the format: d/M/yyyy HHmm. \n Example format: 31/1/2025 2359");
            }
        }

        ui.showLine();
        ui.showMessage(" Bye. Come back when you need me again!");
        ui.showLine();
    }

    /**
     * Forms message displayed to the user when a task is added.
     * Displays the specific task details and the updated total count.
     *
     * @param tasks List containing the newly added task.
     */
    private static void addMessage(ArrayList<Task> tasks) {
        Task task = tasks.get(tasks.size() - 1);
        int count = tasks.size();
        System.out.println(" Got it. I've added this task:");
        System.out.println("   " + task);
        if (count == 1) {
            System.out.println(" Now you have 1 task in the list.");
        } else {
            System.out.println(" Now you have " + count + " tasks in the list.");
        }
    }

    /**
     * Checks if specified index exists within the task list.
     *
     * @param index Index of the task provided by user.
     * @param tasks List of tasks to check against.
     * @throws SasaException If the index is outside the bounds of the list
     */
    private static void checkIndex(int index, ArrayList<Task> tasks) throws SasaException {
        if (index < 0 || index >= tasks.size()) {
            throw new SasaException("Task " + (index + 1) + " doesn't exist! You have " + tasks.size() + " tasks.");
        }
    }
}
