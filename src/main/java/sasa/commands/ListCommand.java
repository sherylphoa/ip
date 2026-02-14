package sasa.commands;

import sasa.storage.Storage;
import sasa.tasks.TaskList;

/**
 * Represents a command to display all tasks currently in the list.
 */
public class ListCommand extends Command {
    @Override
    public void execute(TaskList tasks, Storage storage) {
        this.reply = tasks.listTasks();
    }
}
