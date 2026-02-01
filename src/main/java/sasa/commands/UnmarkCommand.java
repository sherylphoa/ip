package sasa.commands;

import sasa.SasaException;
import sasa.Storage;
import sasa.TaskList;
import sasa.Ui;

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
