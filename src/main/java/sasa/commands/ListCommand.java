package sasa.commands;

import sasa.storage.Storage;
import sasa.TaskList;
import sasa.Ui;

public class ListCommand extends Command {
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        tasks.listTasks(ui);
    }
}
