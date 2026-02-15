package sasa.commands;

import sasa.exception.SasaException;
import sasa.storage.Storage;
import sasa.tasks.TaskList;

/**
 * Represents an executable command within the Sasa application.
 */
public abstract class Command {
    protected String reply = "";

    /**
     * Executes the specific logic of the command.
     *
     * @param tasks   The list of tasks to be operated on.
     * @param storage The storage component to read or write task data.
     * @throws SasaException If an error occurs during execution.
     */
    public abstract void execute(TaskList tasks, Storage storage) throws SasaException;

    /**
     * Returns whether this command should signal the application to terminate.
     *
     * @return true if the application should exit, false otherwise.
     */
    public boolean isExit() {
        return false;
    }

    /**
     * Returns the response message generated after the command is executed.
     * @return The chatbot's reply message.
     */
    public String getReply() {
        return reply;
    }
}
