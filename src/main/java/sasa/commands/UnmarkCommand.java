package sasa.commands;

import sasa.exception.SasaException;
import sasa.storage.Storage;
import sasa.tasks.TaskList;
import sasa.ui.Ui;

/**
 * Represents a command to unmark a specific task, setting it to not completed.
 */
public class UnmarkCommand extends Command {
    private final int targetIndex;

    /**
     * @param targetIndex The zero-based index of the task to unmark.
     */
    public UnmarkCommand(int targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws SasaException {
        this.reply = tasks.unmarkTask(targetIndex, ui);
        storage.saveTasksToFile(tasks.getTasks());
    }
}
