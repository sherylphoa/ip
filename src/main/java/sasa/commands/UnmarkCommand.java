package sasa.commands;

import sasa.exception.SasaException;
import sasa.storage.Storage;
import sasa.tasks.TaskList;
import sasa.ui.Ui;

public class UnmarkCommand extends Command {
    private final int targetIndex;

    public UnmarkCommand(int targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws SasaException {
        tasks.unmarkTask(targetIndex, ui);
        storage.save(tasks.getTasks());
    }
}
