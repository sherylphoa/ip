package sasa.commands;

import sasa.exception.SasaException;
import sasa.storage.Storage;
import sasa.tasks.TaskList;

/**
 * Represents a command to sort the tasks in the task list chronologically.
 */
public class SortCommand extends Command {
    @Override
    public void execute(TaskList tasks, Storage storage) throws SasaException {
        this.reply = tasks.sortTasks();
        storage.saveTasksToFile(tasks.getTasks());
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
