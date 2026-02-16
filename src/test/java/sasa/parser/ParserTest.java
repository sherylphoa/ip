package sasa.parser;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import sasa.exception.SasaException;

public class ParserTest {
    @Test
    public void parseIndex_validNumber_success() throws SasaException {
        // User inputs "5", we expect index 4
        assertEquals(4, Parser.parseIndex("5"));
    }

    @Test
    public void parseIndex_invalidNumber_exceptionThrown() {
        assertThrows(SasaException.class, () -> {
            Parser.parseIndex("abc");
        });
    }
}
