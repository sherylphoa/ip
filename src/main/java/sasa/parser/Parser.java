package sasa.parser;

import sasa.tasks.Deadline;
import sasa.tasks.Event;
import sasa.tasks.Todo;
import sasa.commands.*;
import sasa.exception.SasaException;

/**
 * Deals with making sense of the user command.
 * This class contains static methods to parse raw user input into executable Command objects.
 */
public class Parser {

    /**
     * Parses the full command string entered by the user.
     *
     * @param fullCommand The raw input string from the user.
     * @return A Command object corresponding to the user's intent.
     * @throws SasaException If the command is unrecognized or has invalid arguments.
     */
    public static Command parse(String fullCommand) throws SasaException {
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

            case "find":
                return parseFind(arg);

            default:
                throw new SasaException("I don't know that command!");
        }
    }

    /**
     * Parses the arguments for a todo command.
     *
     * @param args The description of the todo.
     * @return An AddCommand containing the new Todo task.
     * @throws SasaException If the description is empty.
     */
    public static Command parseTodo(String args) throws SasaException {
        if (args.isEmpty()) {
            throw new SasaException("The description of a todo cannot be empty.");
        }
        return new AddCommand(new Todo(args));
    }

    /**
     * Parses the arguments for a deadline command.
     * Expected format: description /by d/M/yyyy HHmm
     *
     * @param args The raw argument string including description and date.
     * @return An AddCommand containing the new Deadline task.
     * @throws SasaException If the description is empty, formatting is wrong, or date is invalid.
     */
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

    /**
     * Parses the arguments for an event command.
     * Expected format: description /from d/M/yyyy HHmm /to d/M/yyyy HHmm
     *
     * @param args The raw argument string including description and time ranges.
     * @return An AddCommand containing the new Event task.
     * @throws SasaException If formatting is wrong or dates are invalid.
     */
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

    /**
     * Converts a string argument into a zero-based integer index.
     *
     * @param args The string representing the task number (e.g., "1").
     * @return The integer index (e.g., 0).
     * @throws SasaException If the input is not a valid integer or is empty.
     */
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

    /**
     * Parses the arguments for a find command.
     *
     * @param arg The search keyword provided by the user.
     * @return A FindCommand initialized with the keyword.
     * @throws SasaException If the keyword is empty.
     */
    private static Command parseFind(String arg) throws SasaException {
        if (arg.isEmpty()) {
            throw new SasaException("The search keyword cannot be empty.");
        }
        return new FindCommand(arg);
    }
}
