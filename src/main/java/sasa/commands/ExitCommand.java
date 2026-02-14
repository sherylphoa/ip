package sasa.commands;

import sasa.storage.Storage;
import sasa.tasks.TaskList;

/**
 * Represents a command to terminate the application.
 */
public class ExitCommand extends Command {
    @Override
    public void execute(TaskList tasks, Storage storage) {
        this.reply = "Bye. Come back when you need me again!";
    }

    @Override
    public boolean isExit() {
        return true;
    }
}
