package sasa.commands;

import sasa.exception.SasaException;
import sasa.storage.Storage;
import sasa.tasks.TaskList;
import sasa.ui.Ui;

/**
 * Represents an executable command within the Sasa application.
 */
public abstract class Command {

    /**
     * Executes the specific logic of the command.
     *
     * @param tasks   The list of tasks to be operated on.
     * @param ui      The user interface for interaction with the user.
     * @param storage The storage component to read or write task data.
     * @throws SasaException If an error occurs during execution.
     */
    public abstract void execute(TaskList tasks, Ui ui, Storage storage) throws SasaException;

    /**
     * Returns whether this command should signal the application to terminate.
     *
     * @return true if the application should exit, false otherwise.
     */
    public boolean isExit() {
        return false;
    }
}
