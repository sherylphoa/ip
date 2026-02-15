package sasa.commands;

import sasa.exception.SasaException;
import sasa.storage.Storage;
import sasa.tasks.TaskList;

/**
 * Represents a command to mark a specific task as completed.
 */
public class MarkCommand extends Command {
    private final int targetIndex;

    /**
     * @param targetIndex The zero-based index of the task to mark as done.
     */
    public MarkCommand(int targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public void execute(TaskList tasks, Storage storage) throws SasaException {
        this.reply = tasks.markTask(targetIndex);
        storage.saveTasksToFile(tasks.getTasks());
    }
}
