package net.de1mos.remotedictionaryservice.cliserver;

import net.de1mos.remotedictionaryservice.cliserver.commands.AddDictionaryCommand;
import net.de1mos.remotedictionaryservice.cliserver.commands.DictionaryCommand;
import net.de1mos.remotedictionaryservice.cliserver.commands.RemoveDictionaryCommand;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public class CommandQueueTest {

    private CommandQueue queue;

    @Before
    public void setUp() throws Exception {
        queue = new CommandQueue();
    }

    @Test
    public void testQueueShouldReturnCommandsInFIFO() throws InterruptedException {
        queue.addToQueue(new AddDictionaryCommand("word1", "trans1"));
        queue.addToQueue(new RemoveDictionaryCommand("word1", "trans1"));
        queue.addToQueue(new AddDictionaryCommand("word2", "trans2"));

        DictionaryCommand first = queue.getNextCommand();
        assertThat(first, is(instanceOf(AddDictionaryCommand.class)));
        assertThat(first.getWord(), equalTo("word1"));

        DictionaryCommand second = queue.getNextCommand();
        assertThat(second, is(instanceOf(RemoveDictionaryCommand.class)));
        assertThat(second.getWord(), equalTo("word1"));

        DictionaryCommand third = queue.getNextCommand();
        assertThat(third, is(instanceOf(AddDictionaryCommand.class)));
        assertThat(third.getWord(), equalTo("word2"));
    }
}