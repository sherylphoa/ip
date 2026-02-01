package sasa.commands;

import sasa.exception.SasaException;
import sasa.storage.Storage;
import sasa.tasks.Task;
import sasa.tasks.TaskList;
import sasa.ui.Ui;

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
