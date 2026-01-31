import java.util.Scanner;
import java.util.ArrayList;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

/**
 * Main class for the Sasa chatbot
 */
public class Sasa {
    private static final Path FILE_PATH = Paths.get(".", "data", "sasa.txt");

    /**
     * Main entry point for the application.
     *
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        String horizontalLine = "____________________________________________________________";
        ArrayList<Task> tasks = new ArrayList<>();

        loadTasks(tasks);
        System.out.println(horizontalLine);
        System.out.println(" Hello! I'm Sasa\n Your wish is my command");
        System.out.println(horizontalLine);

        Scanner sc = new Scanner(System.in);

        while (true) {
            String input = sc.nextLine();

            if (input.equalsIgnoreCase("bye")) {
                break;
            }
            try {
                System.out.println(horizontalLine);
                if (input.equalsIgnoreCase("list")) {
                    System.out.println("Here are your tasks:");
                    for (int i = 0; i < tasks.size(); i++) {
                        System.out.println(" " + (i + 1) + "." + tasks.get(i));
                    }
                } else if (input.startsWith("mark")) {
                    int index = Integer.parseInt(input.substring(5)) - 1;
                    checkIndex(index, tasks);
                    tasks.get(index).markAsDone();
                    System.out.println(" Nice! This task is marked:");
                    System.out.println("   " + tasks.get(index));
                    saveTasks(tasks);
                } else if (input.startsWith("unmark")) {
                    int index = Integer.parseInt(input.substring(7)) - 1;
                    checkIndex(index, tasks);
                    tasks.get(index).unmarkAsDone();
                    System.out.println(" OK, This task is unmarked:");
                    System.out.println("   " + tasks.get(index));
                    saveTasks(tasks);
                } else if (input.startsWith("todo")) {
                    if (input.length() <= 5) {
                        throw new SasaException("Please include your task.");
                    }
                    tasks.add(new Todo(input.substring(5)));
                    addMessage(tasks);
                    saveTasks(tasks);
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
                    saveTasks(tasks);
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
                    saveTasks(tasks);
                } else if (input.startsWith("delete")) {
                    int index = Integer.parseInt(input.substring(7)) - 1;
                    checkIndex(index, tasks);
                    Task removed = tasks.remove(index);
                    System.out.println(" I've removed this task:\n   " + removed);
                    System.out.println(" Now you have " + tasks.size() + " tasks in the list.");
                    saveTasks(tasks);
                } else {
                    throw new SasaException("Sorry, I do not know what that means :(");
                }
                System.out.println(horizontalLine);
            } catch (SasaException e) {
                System.out.println(" OOPSIE! " + e.getMessage());
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException | StringIndexOutOfBoundsException e) {
                System.out.println(" OOPSIE! Please provide a valid task number.");
            }
            catch (java.time.format.DateTimeParseException e) {
                System.out.println(" OOPSIE! I can't understand that date. Use the format: d/M/yyyy HHmm. \n Example format: 31/1/2025 2359");
            }
        }

        System.out.println(horizontalLine);
        System.out.println(" Bye. Come back when you need me again!");
        System.out.println(horizontalLine);

        sc.close();
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

    /**
     * Saves the current task list to the hard disk.
     * Creates the data directory if it does not exist and overwrites the existing file.
     *
     * @param tasks List of tasks to be saved.
     */
    private static void saveTasks(ArrayList<Task> tasks) {
        try {
            Files.createDirectories(FILE_PATH.getParent()); // Create ./data/ if missing
            FileWriter fw = new FileWriter(FILE_PATH.toFile());
            for (Task t : tasks) {
                String type = (t instanceof Todo) ? "T" : (t instanceof Deadline) ? "D" : "E";
                int done = t.isTaskDone() ? 1 : 0;
                String line = type + " | " + done + " | " + t.description;

                if (t instanceof Deadline) {
                    line += " | " + ((Deadline) t).by;
                }
                if (t instanceof Event) {
                    line += " | " + ((Event) t).from + " | " + ((Event) t).to;
                }

                fw.write(line + System.lineSeparator());
            }
            fw.close();
        } catch (IOException e) {
            System.out.println(" Error saving: " + e.getMessage());
        }
    }

    /**
     * Loads tasks from the hard disk into the provided list.
     * If the file does not exist, the method returns without modifying the list.
     *
     * @param tasks List to populate with loaded data.
     */
    private static void loadTasks(ArrayList<Task> tasks) {
        File f = FILE_PATH.toFile();
        if (!f.exists()) return;

        try (Scanner s = new Scanner(f)) {
            while (s.hasNext()) {
                String[] p = s.nextLine().split(" \\| ");
                Task t;
                if (p[0].equals("T")) {
                    t = new Todo(p[2]);
                } else if (p[0].equals("D")) {
                    t = new Deadline(p[2], LocalDateTime.parse(p[3]));
                } else {
                    t = new Event(p[2], LocalDateTime.parse(p[3]), LocalDateTime.parse(p[4]));
                }
                if (p[1].equals("1")) {
                    t.markAsDone();
                }
                tasks.add(t);
            }
        } catch (IOException e) {
            System.out.println(" Error loading data.");
        }
    }
}
