package sasa.tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a task that starts and ends at specified times.
 */
public class Event extends Task {
    private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy, h:mm a");
    protected LocalDateTime startDateTime;
    protected LocalDateTime endDateTime;

    /**
     * Constructs an sasa.tasks.Event task.
     *
     * @param description The description of the event.
     * @param startDateTime The start time/date.
     * @param endDateTime The end time/date.
     */
    public Event(String description, String startDateTime, String endDateTime) {
        super(description);
        this.startDateTime = LocalDateTime.parse(startDateTime, INPUT_FORMAT);
        this.endDateTime = LocalDateTime.parse(endDateTime, INPUT_FORMAT);
    }

    /**
     * Constructs a sasa.tasks.Event task from hard disk.
     *
     * @param description The description of the event.
     * @param startDateTime The start time/date in LocalDateTime.
     * @param endDateTime The end time/date in LocalDateTime.
     */
    public Event(String description, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        super(description);
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    /**
     * Returns the startDateTime of the event.
     *
     * @return The LocalDateTime representing the start.
     */
    public LocalDateTime getStartDateTime() {
        return this.startDateTime;
    }

    /**
     * Returns the endDateTime of the event.
     *
     * @return The LocalDateTime representing the end.
     */
    public LocalDateTime getEndDateTime() {
        return this.endDateTime;
    }

    @Override
    public String toFileFormat() {
        return String.format("E | %d | %s | %s | %s", isDone ? 1 : 0, description, startDateTime, endDateTime);
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + startDateTime.format(OUTPUT_FORMAT)
                + " to: " + endDateTime.format(OUTPUT_FORMAT) + ")";
    }
}
