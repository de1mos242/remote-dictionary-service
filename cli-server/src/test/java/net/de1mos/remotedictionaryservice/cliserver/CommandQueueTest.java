package net.de1mos.remotedictionaryservice.cliserver;

import net.de1mos.remotedictionaryservice.cliserver.commands.AddDictionaryCommand;
import net.de1mos.remotedictionaryservice.cliserver.commands.DictionaryCommand;
import net.de1mos.remotedictionaryservice.cliserver.commands.RemoveDictionaryCommand;
import net.de1mos.remotedictionaryservice.cliserver.exceptions.CommandQueueSizeLimitExceedException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public class CommandQueueTest {

    public static final int MAX_QUEUE_SIZE = 10;
    private CommandQueue queue;

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        queue = new CommandQueue(MAX_QUEUE_SIZE);
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

    @Test
    public void testQueueShouldThrowErrorOnSizeExceed() throws InterruptedException {
        for (int i=0; i< MAX_QUEUE_SIZE; i++) {
            queue.addToQueue(new AddDictionaryCommand("word1", "trans1"));
        }

        exception.expect(CommandQueueSizeLimitExceedException.class);
        queue.addToQueue(new AddDictionaryCommand("word1", "trans1"));
    }
}