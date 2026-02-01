package sasa.commands;

import sasa.storage.Storage;
import sasa.tasks.TaskList;
import sasa.ui.Ui;

public class ExitCommand extends Command {
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.showMessage("Bye. Come back when you need me again!");
    }

    @Override
    public boolean isExit() {
        return true;
    }
}
