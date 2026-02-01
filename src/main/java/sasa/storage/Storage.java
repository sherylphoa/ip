package sasa.storage;

import sasa.tasks.Deadline;
import sasa.tasks.Event;
import sasa.tasks.Task;
import sasa.tasks.Todo;
import sasa.exception.SasaException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDateTime;

/**
 * Handles loading tasks from the file and saving tasks in the file.
 */
public class Storage {
    private final Path filePath;
    private static final String TYPE_TODO = "T";
    private static final String TYPE_DEADLINE = "D";
    private static final String TYPE_EVENT = "E";

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
    public void save(ArrayList<Task> tasks) throws SasaException{
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
                    line += " | " + ((Deadline) t).getBy();
                }
                if (t instanceof Event) {
                    line += " | " + ((Event) t).getFrom() + " | " + ((Event) t).getTo();
                }
                fw.write(line + System.lineSeparator());
            }
            fw.close();
        } catch (IOException e) {
            throw new SasaException (" Error saving: " + e.getMessage());
        }
    }

    /**
     * Loads tasks from the storage file and returns them as an ArrayList.
     * If the file does not exist, it returns an empty list.
     *
     * @return An ArrayList of tasks loaded from the file.
     * @throws SasaException If the file exists but cannot be read or contains malformed data.
     */
    public ArrayList<Task> load()throws SasaException {
        ArrayList<Task> tasks = new ArrayList<>();
        File f = filePath.toFile();
        if (!f.exists()) return tasks;

        try (Scanner s = new Scanner(f)) {
            while (s.hasNext()) {
                String[] part = s.nextLine().split(" \\| ");
                Task t;
                if (part[0].equals(TYPE_TODO)) {
                    t = new Todo(part[2]);
                } else if (part[0].equals(TYPE_DEADLINE)) {
                    t = new Deadline(part[2], LocalDateTime.parse(part[3]));
                } else {
                    t = new Event(part[2], LocalDateTime.parse(part[3]), LocalDateTime.parse(part[4]));
                }
                if (part[1].equals("1")) t.markAsDone();
                tasks.add(t);
            }
        } catch (IOException e) {
            throw new SasaException(" Error loading data.");
        }
        return tasks;
    }
}
