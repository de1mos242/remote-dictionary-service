package net.de1mos.remotedictionaryservice.cliserver;

import net.de1mos.remotedictionaryservice.cliserver.commands.AddDictionaryCommand;
import net.de1mos.remotedictionaryservice.cliserver.commands.RemoveDictionaryCommand;
import net.de1mos.remotedictionaryservice.cliserver.exceptions.CommandQueueSizeLimitExceedException;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public class CommandsExecutorTest {

    private DictionaryRepository dictionaryRepository;

    private CommandQueue queue;

    private CommandsExecutor executor;

    private static final int MAX_QUEUE_SIZE = 1000;

    @Before
    public void setUp() throws Exception {
        queue = new CommandQueue(MAX_QUEUE_SIZE);
        dictionaryRepository = new DictionaryRepository();
        executor = new CommandsExecutor(dictionaryRepository, queue);
    }

    @Test
    public void testApplyOperationsInOrder() throws InterruptedException {
        queue.addToQueue(new AddDictionaryCommand("word1", "trans1"));
        queue.addToQueue(new AddDictionaryCommand("word1", "trans2"));
        queue.addToQueue(new RemoveDictionaryCommand("word1", "trans1"));
        queue.addToQueue(new AddDictionaryCommand("word1", "trans1"));

        executor.ApplyNextCommand();
        assertThat(dictionaryRepository.getTranslations("word1"), hasItem("trans1"));

        executor.ApplyNextCommand();
        assertThat(dictionaryRepository.getTranslations("word1"), hasItem("trans1"));
        assertThat(dictionaryRepository.getTranslations("word1"), hasItem("trans2"));

        executor.ApplyNextCommand();
        assertThat(dictionaryRepository.getTranslations("word1"), not(hasItem("trans1")));

        executor.ApplyNextCommand();
        assertThat(dictionaryRepository.getTranslations("word1"), hasItem("trans1"));
    }

    @Test
    public void testAddTwoSubsequientAddsAndTwoSubsequientRemoves() throws InterruptedException {
        queue.addToQueue(new AddDictionaryCommand("word1", "trans1"));
        queue.addToQueue(new AddDictionaryCommand("word1", "trans1"));
        queue.addToQueue(new RemoveDictionaryCommand("word1", "trans1"));
        queue.addToQueue(new RemoveDictionaryCommand("word1", "trans1"));

        executor.ApplyNextCommand();
        assertThat(dictionaryRepository.getTranslations("word1"), hasItem("trans1"));

        executor.ApplyNextCommand();
        assertThat(dictionaryRepository.getTranslations("word1"), hasItem("trans1"));
        assertThat(dictionaryRepository.getTranslations("word1").size(), is(1));

        executor.ApplyNextCommand();
        assertThat(dictionaryRepository.getTranslations("word1"), not(hasItem("trans1")));
        assertThat(dictionaryRepository.getTranslations("word1").size(), is(0));

        executor.ApplyNextCommand();
        assertThat(dictionaryRepository.getTranslations("word1"), not(hasItem("trans1")));
        assertThat(dictionaryRepository.getTranslations("word1").size(), is(0));
    }

    @Test(expected = CommandQueueSizeLimitExceedException.class)
    public void testQueueShouldRejectCommandsMoreThanMaxLimit() throws InterruptedException {
        for (int i=0; i < MAX_QUEUE_SIZE; i++) {
            queue.addToQueue(new AddDictionaryCommand("word1", "trans1"));
        }

        queue.addToQueue(new AddDictionaryCommand("word1", "trans1"));
    }

}