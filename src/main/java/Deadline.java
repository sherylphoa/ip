/**
 * Represents a task that must be completed by a specified deadline.
 */
public class Deadline extends Task {
    protected String by;

    /**
     * @param description The description of the deadline.
     * @param by The date/time the task is due.
     */
    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by + ")";
    }
}
