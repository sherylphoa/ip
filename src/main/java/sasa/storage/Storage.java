package sasa.storage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

import sasa.exception.SasaException;
import sasa.tasks.Deadline;
import sasa.tasks.Event;
import sasa.tasks.Task;
import sasa.tasks.Todo;


/**
 * Handles loading tasks from the file and saving tasks in the file.
 */
public class Storage {
    private static final String TYPE_TODO = "T";
    private static final String TYPE_DEADLINE = "D";
    private static final String TYPE_EVENT = "E";
    private final Path filePath;

    /**
     * Constructs a Storage object with the specified file path.
     *
     * @param filePath The string path of the storage file (e.g., "./data/sasa.txt").
     */
    public Storage(String filePath) {
        this.filePath = Paths.get(filePath);
    }

    /**
     * Saves the current list of tasks to the storage file.
     * If the directory or file does not exist, they will be created.
     *
     * @param tasks The list of tasks to be written to the file.
     * @throws SasaException If an error occurs while writing to the file or creating directories.
     */
    public void saveTasksToFile(ArrayList<Task> tasks) throws SasaException {
        assert tasks != null : "Cannot save a null task list";
        try {
            Files.createDirectories(filePath.getParent());
            FileWriter fw = new FileWriter(filePath.toFile());
            for (Task t : tasks) {
                String type;
                if (t instanceof Todo) {
                    type = TYPE_TODO;
                } else if (t instanceof Deadline) {
                    type = TYPE_DEADLINE;
                } else {
                    type = TYPE_EVENT;
                }
                int done = t.isTaskDone() ? 1 : 0;
                String line = type + " | " + done + " | " + t.getDescription();
                if (t instanceof Deadline) {
                    line += " | " + ((Deadline) t).getDeadlineDateTime();
                }
                if (t instanceof Event) {
                    line += " | " + ((Event) t).getStartDateTime() + " | " + ((Event) t).getEndDateTime();
                }
                fw.write(line + System.lineSeparator());
            }
            fw.close();
        } catch (IOException e) {
            throw new SasaException(" Error saving: " + e.getMessage());
        }
    }

    /**
     * Loads tasks from the storage file and returns them as an ArrayList.
     * If the file does not exist, it returns an empty list.
     *
     * @return An ArrayList of tasks loaded from the file.
     * @throws SasaException If the file exists but cannot be read or contains malformed data.
     */
    public ArrayList<Task> loadTasks() throws SasaException {
        assert filePath != null : "Storage file path must not be null";
        ArrayList<Task> tasks = new ArrayList<>();
        File f = filePath.toFile();
        if (!f.exists()) {
            return tasks;
        }

        try (Scanner s = new Scanner(f)) {
            while (s.hasNext()) {
                String[] parts = s.nextLine().split(" \\| ");
                assert parts.length >= 3 : "Malformed line in storage file: insufficient data fields";
                assert parts[0].matches("[TDE]") : "Unknown task type in storage file: " + parts[0];
                Task t;
                if (parts[0].equals(TYPE_TODO)) {
                    t = new Todo(parts[2]);
                } else if (parts[0].equals(TYPE_DEADLINE)) {
                    t = new Deadline(parts[2], LocalDateTime.parse(parts[3]));
                } else {
                    t = new Event(parts[2], LocalDateTime.parse(parts[3]), LocalDateTime.parse(parts[4]));
                }
                if (parts[1].equals("1")) {
                    t.markAsDone();
                }
                tasks.add(t);
            }
        } catch (IOException e) {
            throw new SasaException(" Error loading data.");
        }
        assert tasks != null : "Should return a valid list even if empty";
        return tasks;
    }
}
