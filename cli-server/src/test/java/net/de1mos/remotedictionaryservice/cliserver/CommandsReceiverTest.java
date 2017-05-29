package net.de1mos.remotedictionaryservice.cliserver;

import net.de1mos.remotedictionaryservice.cliserver.commands.AddDictionaryCommand;
import net.de1mos.remotedictionaryservice.cliserver.commands.RemoveDictionaryCommand;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public class CommandsReceiverTest {

    private CommandQueue queue;
    private CommandsReceiver receiver;

    @Before
    public void setUp() throws Exception {
        queue = new CommandQueue();
        receiver = new CommandsReceiver(queue);
    }

    @Test
    public void receiveOneCommand() throws Exception {
        receiver.receiveCommand(new AddDictionaryCommand("word1", "trans1"));

        assertThat(queue.getNextCommand(), is(instanceOf(AddDictionaryCommand.class)));
    }

    @Test
    public void receiveCommands() throws Exception {
        receiver.receiveCommands(Arrays.asList(
                new AddDictionaryCommand("word1", "trans1"),
                new RemoveDictionaryCommand("word1", "trans1")
        ));

        assertThat(queue.getNextCommand(), is(instanceOf(AddDictionaryCommand.class)));
        assertThat(queue.getNextCommand(), is(instanceOf(RemoveDictionaryCommand.class)));
    }

}