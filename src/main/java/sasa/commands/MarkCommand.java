package sasa.commands;

import sasa.SasaException;
import sasa.Storage;
import sasa.TaskList;
import sasa.Ui;

public class MarkCommand extends Command {
    private final int targetIndex;

    public MarkCommand(int targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws SasaException {
        tasks.markTask(targetIndex, ui);
        storage.save(tasks.getTasks());
    }
}
