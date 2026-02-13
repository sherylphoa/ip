package sasa.tasks;

import java.util.ArrayList;

import sasa.exception.SasaException;
import sasa.ui.Ui;

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
     * @param ui   The UI instance to handle message display.
     * @return The chatbot's reply
     */
    public String addTask(Task task, Ui ui) {
        assert tasks != null : "Internal task list should not be null";
        int initialSize = tasks.size();
        tasks.add(task);
        assert tasks.size() == initialSize + 1 : "List size should increase by 1 after adding";
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
     * @param ui    The UI instance to handle message display.
     * @return The chatbot's reply.
     * @throws SasaException If the index is out of bounds.
     */
    public String deleteTask(int index, Ui ui) throws SasaException {
        assert tasks != null : "Task list internal array should be initialized";
        int initialSize = tasks.size();
        checkIndex(index);
        Task removed = tasks.remove(index);
        assert tasks.size() < initialSize : "List size should decrease after deletion";
        return " I've removed this task:\n   " + removed + "\n"
                + " Now you have " + tasks.size() + " tasks in the list.";
    }

    /**
     * Marks a task at a specified index as done.
     *
     * @param index The zero-based index of the task.
     * @param ui    The UI instance to handle message display.
     * @return The Chatbot's reply.
     * @throws SasaException If the index is out of bounds.
     */
    public String markTask(int index, Ui ui) throws SasaException {
        checkIndex(index);
        tasks.get(index).markAsDone();
        assert tasks.get(index).isTaskDone() : "Task should be marked as done";
        return " Nice! This task is marked:" + "\n" + "   " + tasks.get(index);
    }

    /**
     * Marks a task at a specified index as not done.
     *
     * @param index The zero-based index of the task.
     * @param ui    The UI instance to handle message display.
     * @return The chatbot's reply.
     * @throws SasaException If the index is out of bounds.
     */
    public String unmarkTask(int index, Ui ui) throws SasaException {
        checkIndex(index);
        tasks.get(index).unmarkAsDone();
        assert !tasks.get(index).isTaskDone() : "Task should be marked as not done";
        return " OK, This task is unmarked:" + "\n" + "   " + tasks.get(index);
    }

    /**
     * Displays all tasks in the list to the user.
     *
     * @param ui The UI instance used to print the task list.
     * @return The chatbot's reply.
     */
    public String listTasks(Ui ui) {
        if (tasks.isEmpty()) {
            return " Your list is currently empty!";
        } else {
            String message = " Here are your tasks:";
            for (int i = 0; i < tasks.size(); i++) {
                message = message + "\n" + " " + (i + 1) + "." + tasks.get(i);
            }
            return message;
        }
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
     * @param ui The UI used to display the matching tasks.
     * @return The chatbot's reply
     */
    public String findTasks(String keyword, Ui ui) {
        ArrayList<Task> matchingTasks = new ArrayList<>();
        for (Task task : tasks) {
            if (task.getDescription().toLowerCase().contains(keyword.toLowerCase())) {
                matchingTasks.add(task);
            }
        }
        for (Task t : matchingTasks) {
            assert t.getDescription().toLowerCase().contains(keyword.toLowerCase())
                    : "Matching task does not contain the search keyword";
        }
        if (matchingTasks.isEmpty()) {
            return " There are no matching tasks in your list.";
        } else {
            String message = " Here are the matching tasks in your list:";
            for (int i = 0; i < matchingTasks.size(); i++) {
                message = message + "\n" + " " + (i + 1) + "." + matchingTasks.get(i);
            }
            return message;
        }
    }
}
