package sasa.commands;

import sasa.storage.Storage;
import sasa.tasks.TaskList;
import sasa.ui.Ui;

/**
 * Represents a command to search for tasks containing a specific keyword.
 */
public class FindCommand extends Command {
    private final String keyword;

    /**
     * Constructs a FindCommand with the specified search keyword.
     *
     * @param keyword The string to search for within task descriptions.
     */
    public FindCommand(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        this.reply = tasks.findTasks(keyword, ui);
    }
}
