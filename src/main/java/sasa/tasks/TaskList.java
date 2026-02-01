package sasa.tasks;

import sasa.ui.Ui;
import sasa.exception.SasaException;

import java.util.ArrayList;

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

    public void addTask(Task task, Ui ui) {
        tasks.add(task);
        ui.showMessage(" Got it. I've added this task:");
        ui.showMessage("   " + task);
        if (tasks.size() == 1) {
            System.out.println(" Now you have 1 task in the list.");
        } else {
            System.out.println(" Now you have " + tasks.size() + " tasks in the list.");
        }
    }

    public Task deleteTask(int index, Ui ui) throws SasaException {
        checkIndex(index);
        Task removed = tasks.remove(index);
        ui.showMessage(" I've removed this task:\n   " + removed);
        ui.showMessage(" Now you have " + tasks.size() + " tasks in the list.");
        return removed;
    }

    public void markTask(int index, Ui ui) throws SasaException {
        checkIndex(index);
        tasks.get(index).markAsDone();
        ui.showMessage(" Nice! This task is marked:");
        ui.showMessage("   " + tasks.get(index));
    }

    public void unmarkTask(int index, Ui ui) throws SasaException {
        checkIndex(index);
        tasks.get(index).unmarkAsDone();
        ui.showMessage(" OK, This task is unmarked:");
        ui.showMessage("   " + tasks.get(index));
    }

    public void listTasks(Ui ui) {
        if (tasks.isEmpty()) {
            ui.showMessage(" Your list is currently empty!");
            return;
        }
        ui.showMessage(" Here are your tasks:");
        for (int i = 0; i < tasks.size(); i++) {
            ui.showMessage(" " + (i + 1) + "." + tasks.get(i));
        }
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    private void checkIndex(int index) throws SasaException {
        if (index < 0 || index >= tasks.size()) {
            throw new SasaException("sasa.tasks.Task " + (index + 1) + " doesn't exist! You have " + tasks.size() + " tasks.");
        }
    }

    /**
     * Finds and displays tasks that contain the given keyword in their description.
     *
     * @param keyword The string to search for.
     * @param ui The UI used to display the matching tasks.
     */
    public void findTasks(String keyword, Ui ui) {
        ArrayList<Task> matchingTasks = new ArrayList<>();
        for (Task task : tasks) {
            if (task.getDescription().toLowerCase().contains(keyword.toLowerCase())) {
                matchingTasks.add(task);
            }
        }
        ui.showMatchingTasks(matchingTasks);
    }
}
