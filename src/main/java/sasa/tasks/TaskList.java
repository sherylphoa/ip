package sasa.tasks;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.stream.IntStream;

import sasa.exception.SasaException;

/**
 * Manages the collection of tasks in the Sasa application.
 */
public class TaskList {
    private final ArrayList<Task> tasks;

    /**
     * Constructor for a new list.
     * */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Constructor for loading existing tasks.
     * */
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Adds a task to the list and displays a confirmation message.
     *
     * @param task The task to be added.
     * @return The chatbot's reply
     */
    public String addTask(Task task) {
        assert tasks != null : "Internal task list should not be null";
        int initialSize = tasks.size();
        tasks.add(task);
        assert tasks.size() == initialSize + 1 : "List size should increase by 1 after adding";
        return generateAddMessage(task);
    }

    private String generateAddMessage(Task task) {
        String addMessage = " Got it. I've added this task: \n" + task;
        if (tasks.size() == 1) {
            return addMessage + "\n" + " Now you have 1 task in the list.";
        } else {
            return addMessage + "\n" + " Now you have " + tasks.size() + " tasks in the list.";
        }
    }

    /**
     * Removes a task from the list based on its index.
     *
     * @param index The zero-based index of the task to be deleted.
     * @return The chatbot's reply.
     * @throws SasaException If the index is out of bounds.
     */
    public String deleteTask(int index) throws SasaException {
        assert tasks != null : "Task list internal array should be initialized";
        int initialSize = tasks.size();
        checkIndex(index);
        Task removed = tasks.remove(index);
        assert tasks.size() < initialSize : "List size should decrease after deletion";
        return generateDeleteMessage(removed);
    }

    private String generateDeleteMessage(Task task) {
        return " I've removed this task:\n   " + task + "\n"
                + " Now you have " + tasks.size() + " tasks in the list.";
    }

    /**
     * Marks a task at a specified index as done.
     *
     * @param index The zero-based index of the task.
     * @return The Chatbot's reply.
     * @throws SasaException If the index is out of bounds.
     */
    public String markTask(int index) throws SasaException {
        checkIndex(index);
        tasks.get(index).markAsDone();
        assert tasks.get(index).isTaskDone() : "Task should be marked as done";
        return " Nice! This task is marked:" + "\n" + "   " + tasks.get(index);
    }

    /**
     * Marks a task at a specified index as not done.
     *
     * @param index The zero-based index of the task.
     * @return The chatbot's reply.
     * @throws SasaException If the index is out of bounds.
     */
    public String unmarkTask(int index) throws SasaException {
        checkIndex(index);
        tasks.get(index).unmarkAsDone();
        assert !tasks.get(index).isTaskDone() : "Task should be marked as not done";
        return " OK, This task is unmarked:" + "\n" + "   " + tasks.get(index);
    }

    /**
     * Displays all tasks in the list to the user.
     *
     * @return The chatbot's reply.
     */
    public String listTasks() {
        if (tasks.isEmpty()) {
            return " Your list is currently empty!";
        }
        String taskList = IntStream.range(0, tasks.size())
                .mapToObj(i -> String.format("\n %d.%s", i + 1, tasks.get(i)))
                .reduce("", (curr, taskString) -> curr + taskString);

        return " Here are your tasks:" + taskList;
    }

    /**
     * Returns the raw list of tasks.
     *
     * @return An ArrayList containing all tasks.
     */
    public ArrayList<Task> getTasks() {
        return tasks;
    }

    /**
     * Validates if the given index exists within the task list.
     *
     * @param index The zero-based index to check.
     * @throws SasaException If the index is negative or exceeds the current list size.
     */
    private void checkIndex(int index) throws SasaException {
        assert index >= 0 : "Index should be non-negative; check Parser logic.";
        if (index < 0 || index >= tasks.size()) {
            throw new SasaException("sasa.tasks.Task " + (index + 1)
                    + " doesn't exist! You have " + tasks.size() + " tasks.");
        }
    }

    /**
     * Finds and displays tasks that contain the given keyword in their description.
     *
     * @param keyword The string to search for.
     * @return The chatbot's reply
     */
    public String findTasks(String keyword) {
        String matchingTasks = IntStream.range(0, tasks.size())
                .filter(i -> tasks.get(i).getDescription().toLowerCase()
                        .contains(keyword.toLowerCase()))
                .mapToObj(i -> (i + 1) + "." + tasks.get(i))
                .reduce("", (curr, taskStr) -> curr + "\n " + taskStr);

        return matchingTasks.isEmpty()
                ? " There are no matching tasks."
                : " Here are the matching tasks:" + matchingTasks;
    }

    /**
     * Sorts the tasks in the list chronologically by their start date and time.
     *
     * Deadlines are sorted by their due date, and Events are sorted by their start date.
     * If two Events have the same start time, the one that ends earlier is placed first.
     * Tasks without dates (e.g., Todos) are moved to the end of the list.
     *
     * @return The sorted list.
     */
    public String sortTasks() {
        if (tasks.isEmpty()) {
            return " Your list is empty, nothing to sort!";
        }

        tasks.sort((t1, t2) -> {
            LocalDateTime start1 = getTaskDate(t1);
            LocalDateTime start2 = getTaskDate(t2);

            if (start1 == null && start2 == null) {
                return 0;
            }
            if (start1 == null) {
                return 1;
            }
            if (start2 == null) {
                return -1;
            }
            int startCompare = start1.compareTo(start2);

            if (startCompare == 0) {
                LocalDateTime end1 = getTaskEndDate(t1);
                LocalDateTime end2 = getTaskEndDate(t2);

                if (end1 != null && end2 != null) {
                    return end1.compareTo(end2); // Earlier end time comes first
                }
            }

            return startCompare;
        });

        return listTasks();
    }

    /**
     * Retrieves the primary date and time associated with a task for sorting.
     *
     * @param t The task to extract the date from.
     * @return The LocalDateTime of the Deadline or the start of an Event or null for Todos;
     */
    private LocalDateTime getTaskDate(Task t) {
        if (t instanceof Deadline) {
            return ((Deadline) t).getDeadlineDateTime();
        } else if (t instanceof Event) {
            return ((Event) t).getStartDateTime();
        }
        return null;
    }

    /**
     * Retrieves the end date and time for events.
     *
     * @param t The task to extract the end date from.
     * @return The LocalDateTime representing the end of an Event or null for Deadlines and Todos;
     */
    private LocalDateTime getTaskEndDate(Task t) {
        if (t instanceof Event) {
            return ((Event) t).getEndDateTime(); // Assuming this getter returns LocalDateTime
        }
        return null;
    }
}
