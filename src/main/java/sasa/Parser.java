package sasa;

import sasa.commands.*;

public class Parser {
    public static Command parse(String fullCommand) throws SasaException{
        String[] components = fullCommand.trim().split(" ", 2);
        String command = components[0].toLowerCase();
        String arg = components.length > 1 ? components[1] : "";

        switch(command) {
            case "bye":
                return new ExitCommand();

            case "list":
                return new ListCommand();

            case "mark":
                return new MarkCommand(parseIndex(arg));

            case "unmark":
                return new UnmarkCommand(parseIndex(arg));

            case "todo":
                return parseTodo(arg);

            case "deadline":
                return parseDeadline(arg);

            case "event":
                return parseEvent(arg);

            case "delete":
                return new DeleteCommand(parseIndex(arg));

            default:
                throw new SasaException("I don't know that command!");
        }
    }

    public static Command parseTodo(String args) throws SasaException {
        if (args.isEmpty()) {
            throw new SasaException("The description of a todo cannot be empty.");
        }
        return new AddCommand(new Todo(args));
    }

    public static Command parseDeadline(String args) throws SasaException {
        if (args.isEmpty()) {
            throw new SasaException("The description of a deadline cannot be empty.");
        }
        if (!args.contains(" /by ")) {
            throw new SasaException("Deadlines must include ' /by ' followed by a date.");
        }
        String[] parts = args.split(" /by ");
        try {
            return new AddCommand(new Deadline(parts[0], parts[1]));
        } catch (java.time.format.DateTimeParseException e) {
            throw new SasaException("I can't understand that date. Use the format: d/M/yyyy HHmm.\n Example: 31/1/2025 2359");
        }
    }

    public static Command parseEvent(String args) throws SasaException {
        if (args.isEmpty()) {
            throw new SasaException("The description of an event cannot be empty.");
        }
        if (!args.contains(" /from ") || !args.contains(" /to ")) {
            throw new SasaException("Events must include ' /from ' and ' /to '.");
        }
        String[] parts = args.split(" /from ");
        String[] timeParts = parts[1].split(" /to ");
        try {
            return new AddCommand(new Event(parts[0], timeParts[0], timeParts[1]));
        } catch (java.time.format.DateTimeParseException e) {
            throw new SasaException("I can't understand that date. Use the format: d/M/yyyy HHmm.\n Example: 31/1/2025 2359");
        }
    }

    public static int parseIndex(String args) throws SasaException {
        if (args.isEmpty()) {
            throw new SasaException("Please provide a task number.");
        }
        try {
            return Integer.parseInt(args) - 1;
        } catch (NumberFormatException e) {
            throw new SasaException("That's not a valid number!");
        }
    }
}
