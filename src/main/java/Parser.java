public class Parser {
    public static String[] parseInput(String fullCommand) {
        return fullCommand.trim().split(" ", 2);
    }

    public static String[] parseDeadline(String args) throws SasaException {
        if (args.isEmpty()) {
            throw new SasaException("The description of a deadline cannot be empty.");
        }
        if (!args.contains(" /by ")) {
            throw new SasaException("Deadlines must include ' /by ' followed by a date.");
        }
        return args.split(" /by ");
    }

    public static String[] parseEvent(String args) throws SasaException {
        if (args.isEmpty()) {
            throw new SasaException("The description of an event cannot be empty.");
        }
        if (!args.contains(" /from ") || !args.contains(" /to ")) {
            throw new SasaException("Events must include ' /from ' and ' /to '.");
        }
        String[] parts = args.split(" /from ");
        String[] timeParts = parts[1].split(" /to ");
        return new String[]{parts[0], timeParts[0], timeParts[1]};
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
