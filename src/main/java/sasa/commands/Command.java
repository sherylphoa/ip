package sasa.commands;

import sasa.exception.SasaException;
import sasa.Storage;
import sasa.TaskList;
import sasa.Ui;

public abstract class Command {
    public abstract void execute(TaskList tasks, Ui ui, Storage storage) throws SasaException;

    public boolean isExit() {
        return false;
    }
}
