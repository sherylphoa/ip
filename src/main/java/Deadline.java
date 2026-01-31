import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents a task that must be completed by a specified deadline.
 */
public class Deadline extends Task {
    protected LocalDate by;

    /**
     * Constructs a Deadline task.
     *
     * @param description The description of the deadline.
     * @param by The date/time the task is due.
     */
    public Deadline(String description, String by) {
        super(description);
        this.by = LocalDate.parse(by);
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by.format(DateTimeFormatter.ofPattern("MMM dd yyyy")) + ")";
    }
}
