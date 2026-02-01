package sasa.commands;

import sasa.*;
import sasa.exception.SasaException;
import sasa.storage.Storage;

public class AddCommand extends Command {
    private final Task task;

    public AddCommand(Task task) {
        this.task = task;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws SasaException {
        tasks.addTask(task, ui);
        storage.save(tasks.getTasks());
    }
}
