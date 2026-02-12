package sasa.commands;

import sasa.storage.Storage;
import sasa.tasks.TaskList;
import sasa.ui.Ui;

/**
 * Represents a command to display all tasks currently in the list.
 */
public class ListCommand extends Command {
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        this.reply = tasks.listTasks(ui);
    }
}
