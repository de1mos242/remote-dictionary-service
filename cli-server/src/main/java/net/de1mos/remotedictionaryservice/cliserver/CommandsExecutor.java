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
     * @return true if there was a command in queue and it was applied, false otherwise
     */
    public boolean ApplyNextCommand() {
        DictionaryCommand command = queue.getNextCommand();
        if (command != null) {
            applyCommand(command);
            return true;
        }
        return false;
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
