package sasa.tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a task that must be completed by a specified deadline.
 */
public class Deadline extends Task {
    private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy, h:mm a");
    protected LocalDateTime deadlineDateTime;

    /**
     * Constructs a sasa.tasks.Deadline task.
     *
     * @param description The description of the deadline.
     * @param deadlineDateTime The date/time the task is due.
     */
    public Deadline(String description, String deadlineDateTime) {
        super(description);
        this.deadlineDateTime = LocalDateTime.parse(deadlineDateTime, INPUT_FORMAT);
    }

    /**
     * Constructs a sasa.tasks.Deadline task from hard disk.
     *
     * @param description The description of the deadline.
     * @param deadlineDateTime The date/time the task is due with LocalDateTime data type.
     */
    public Deadline(String description, LocalDateTime deadlineDateTime) {
        super(description);
        this.deadlineDateTime = deadlineDateTime;
    }

    /**
     * Returns the deadlineDateTime time of the deadline.
     *
     * @return The LocalDateTime representing the by time.
     */
    public LocalDateTime getDeadlineDateTime() {
        return this.deadlineDateTime;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + deadlineDateTime.format(OUTPUT_FORMAT) + ")";
    }
}
