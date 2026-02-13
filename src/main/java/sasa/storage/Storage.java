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
        try {
            Files.createDirectories(filePath.getParent());
            FileWriter fw = new FileWriter(filePath.toFile());
            for (Task t : tasks) {
                fw.write(t.toFileFormat() + System.lineSeparator());
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
    public ArrayList<Task> load() throws SasaException {
        ArrayList<Task> tasks = new ArrayList<>();
        File f = filePath.toFile();
        if (!f.exists()) {
            return tasks;
        }
        try (Scanner s = new Scanner(f)) {
            while (s.hasNext()) {
                tasks.add(parseTaskFromLine(s.nextLine()));
            }
        } catch (IOException e) {
            throw new SasaException(" Error loading data.");
        }
        return tasks;
    }

    private Task parseTaskFromLine(String line) throws SasaException {
        String[] parts = line.split(" \\| ");
        assert parts.length >= 3;
        Task t = createTaskByType(parts);
        if (parts[1].equals("1")) {
            t.markAsDone();
        }
        return t;
    }

    private Task createTaskByType(String[] parts) throws SasaException {
        String type = parts[0];
        String description = parts[2];

        switch (type) {
        case TYPE_TODO:
            return new Todo(description);
        case TYPE_DEADLINE:
            return new Deadline(description, LocalDateTime.parse(parts[3]));
        case TYPE_EVENT:
            return new Event(description, LocalDateTime.parse(parts[4]), LocalDateTime.parse(parts[5]));
        default:
            throw new SasaException("Unknown task type in storage file: " + type);
        }
    }
}
