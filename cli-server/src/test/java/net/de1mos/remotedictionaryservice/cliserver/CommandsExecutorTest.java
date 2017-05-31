package net.de1mos.remotedictionaryservice.cliserver;

import net.de1mos.remotedictionaryservice.cliserver.commands.AddDictionaryCommand;
import net.de1mos.remotedictionaryservice.cliserver.commands.RemoveDictionaryCommand;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

public class CommandsExecutorTest {

    private DictionaryRepository dictionaryRepository;

    private CommandQueue queue;

    private CommandsExecutor executor;

    @Before
    public void setUp() throws Exception {
        queue = new CommandQueue();
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

}