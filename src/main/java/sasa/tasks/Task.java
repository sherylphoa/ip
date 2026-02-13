package sasa.tasks;

/**
 * Represents a generic task in the sasa.Sasa chatbot.
 * Stores the description and completion status.
 */
public abstract class Task {
    protected String description;
    protected boolean isDone;

    /**
     * Constructs a new sasa.tasks.Task with the given description.
     * The task is initialized as not done.
     *
     * @param description The  description of the task.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Returns the status of the task based on its completion status.
     *
     * @return "X" if the task is done, " " (space) otherwise.
     */
    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    /**
     * Marks the task as completed.
     */
    public void markAsDone() {
        this.isDone = true;
    }

    /**
     * Marks the task as not completed.
     */
    public void unmarkAsDone() {
        this.isDone = false;
    }

    /**
     * Checks if the task is completed.
     *
     * @return true if the task is marked as done, false otherwise.
     */
    public boolean isTaskDone() {
        return isDone;
    }

    /**
     * Returns the description of the task.
     *
     * @return The task description string.
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Returns a string formatted for saving the task to a data file.
     * The format follows the pattern: TYPE | IS_DONE | DESCRIPTION | ...
     *
     * @return A string representing the task in storage format.
     */
    public abstract String toFileFormat();

    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }
}
