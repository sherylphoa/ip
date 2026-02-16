package sasa.parser;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import sasa.commands.AddCommand;
import sasa.commands.ExitCommand;
import sasa.exception.SasaException;

public class ParserTest {
    @Test
    public void parseIndex_validNumber_success() throws SasaException {
        assertEquals(4, Parser.parseIndex("5"));
    }

    @Test
    public void parseIndex_invalidNumber_exceptionThrown() {
        assertThrows(SasaException.class, () -> {
            Parser.parseIndex("abc");
        });
    }

    @Test
    public void parse_todoCommand_returnAddCommand() throws SasaException {
        assertTrue(Parser.parse("todo borrow book") instanceof AddCommand);
    }

    @Test
    public void parse_byeCommand_returnExitCommand() throws SasaException {
        assertTrue(Parser.parse("bye") instanceof ExitCommand);
    }
}
