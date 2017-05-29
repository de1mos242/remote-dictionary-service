package net.de1mos.remotedictionaryservice.cliserver;

import net.de1mos.remotedictionaryservice.cliserver.commands.AddDictionaryCommand;
import net.de1mos.remotedictionaryservice.cliserver.commands.DictionaryCommand;
import net.de1mos.remotedictionaryservice.cliserver.commands.RemoveDictionaryCommand;

/**
 * Read commands from queue one-by-one and apply them to dictionary repository
 */
public class CommandsExecutor {

    private DictionaryRepository repository;

    private CommandQueue queue;

    public CommandsExecutor(DictionaryRepository repository, CommandQueue queue) {
        this.repository = repository;
        this.queue = queue;
    }

    /***
     * Apply next command from queue to dictionary
     * Queue is blocking, so apply will blocked until command is appeared in another thread
     */
    public void ApplyNextCommand() throws InterruptedException {
        DictionaryCommand command = queue.getNextCommand();
        applyCommand(command);
    }

    private void applyCommand(DictionaryCommand command) {
        if (command instanceof AddDictionaryCommand) {
            applyAddCommand((AddDictionaryCommand) command);
        }
        else if (command instanceof RemoveDictionaryCommand) {
            applyRemoveCommand((RemoveDictionaryCommand) command);
        }
        else {
            throw new UnsupportedOperationException(String.format("Command class not supported %s", command.getClass().getCanonicalName()));
        }
    }

    private void applyAddCommand(AddDictionaryCommand command) {
        repository.addTranslation(command.getWord(), command.getTranslation());
    }

    private void applyRemoveCommand(RemoveDictionaryCommand command) {
        repository.removeTranslation(command.getWord(), command.getTranslation());
    }
}
