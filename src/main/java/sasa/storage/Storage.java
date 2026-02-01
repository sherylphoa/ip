package sasa.storage;

import sasa.Deadline;
import sasa.Event;
import sasa.Task;
import sasa.Todo;
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

public class Storage {
    private final Path filePath;

    public Storage(String filePath) {
        this.filePath = Paths.get(filePath);
    }

    public void save(ArrayList<Task> tasks) {
        try {
            Files.createDirectories(filePath.getParent());
            FileWriter fw = new FileWriter(filePath.toFile());
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

    public ArrayList<Task> load()throws SasaException {
        ArrayList<Task> tasks = new ArrayList<>();
        File f = filePath.toFile();
        if (!f.exists()) return tasks;

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
                if (p[1].equals("1")) t.markAsDone();
                tasks.add(t);
            }
        } catch (IOException e) {
            throw new SasaException(" Error loading data.");
        }
        return tasks;
    }
}
