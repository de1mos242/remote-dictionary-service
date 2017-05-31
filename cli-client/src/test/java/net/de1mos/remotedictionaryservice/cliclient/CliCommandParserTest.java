package net.de1mos.remotedictionaryservice.cliclient;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class CliCommandParserTest {

    @Test
    public void assertInvalidCommand() {
        assertFalse(getCliCommandParser("smth").isValid());
    }

    @Test
    public void assertValidGetCommand() {
        final CliCommandParser parser = getCliCommandParser("gEt word");
        assertTrue(parser.isValid());
        assertEquals("word", parser.getWord());
        assertEquals(CommandType.GET, parser.getCommandType());
    }

    @Test
    public void assertInvalidGetCommands() {
        assertFalse(getCliCommandParser("get").isValid());
        assertFalse(getCliCommandParser("get w1 w2").isValid());
    }

    @Test
    public void testAddValidCommand1Word() {
        final CliCommandParser parser = getCliCommandParser("adD word MyWord");
        assertTrue(parser.isValid());
        assertEquals(CommandType.ADD, parser.getCommandType());
        assertEquals("word", parser.getWord());
        assertEquals(1, parser.getAddedTranslations().size());
        assertEquals("MyWord", parser.getAddedTranslations().get(0));
    }

    @Test
    public void testAddValidCommand2Word() {
        final CliCommandParser parser = getCliCommandParser("adD word MyWord another");
        assertTrue(parser.isValid());
        assertEquals(CommandType.ADD, parser.getCommandType());
        assertEquals("word", parser.getWord());
        assertEquals(2, parser.getAddedTranslations().size());
        assertEquals("MyWord", parser.getAddedTranslations().get(0));
        assertEquals("another", parser.getAddedTranslations().get(1));
    }

    @Test
    public void testAddInvalidCommands() {
        assertFalse(getCliCommandParser("add").isValid());
        assertFalse(getCliCommandParser("add word").isValid());
    }

    @Test
    public void testRemoveValidCommand1word() {
        final CliCommandParser parser = getCliCommandParser("DELETe word MyWord");
        assertTrue(parser.isValid());
        assertEquals(CommandType.REMOVE, parser.getCommandType());
        assertEquals("word", parser.getWord());
        assertEquals(1, parser.getRemoveTranslations().size());
        assertEquals("MyWord", parser.getRemoveTranslations().get(0));
    }

    @Test
    public void testRemoveValidCommand2words() {
        final CliCommandParser parser = getCliCommandParser("DELETe word MyWord second");
        assertTrue(parser.isValid());
        assertEquals(CommandType.REMOVE, parser.getCommandType());
        assertEquals("word", parser.getWord());
        assertEquals(2, parser.getRemoveTranslations().size());
        assertEquals("MyWord", parser.getRemoveTranslations().get(0));
        assertEquals("second", parser.getRemoveTranslations().get(1));
    }

    @Test
    public void testRemoveInvalidCommands() {
        assertFalse(getCliCommandParser("delete").isValid());
        assertFalse(getCliCommandParser("delete word").isValid());
    }

    private CliCommandParser getCliCommandParser(String cmdLine) {
        return new CliCommandParser(Arrays.asList(cmdLine.split(" ")));
    }
}