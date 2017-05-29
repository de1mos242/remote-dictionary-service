package net.de1mos.remotedictionaryservice.cliserver;

import net.de1mos.remotedictionaryservice.cliserver.commands.DictionaryCommand;

import java.util.List;

/**
 * Receive commands and pass them to the command queue
 */
public class CommandsReceiver {

    private CommandQueue queue;

    public CommandsReceiver(CommandQueue queue) {
        this.queue = queue;
    }

    public void receiveCommand(DictionaryCommand command) throws InterruptedException {
        queue.addToQueue(command);
    }

    public void receiveCommands(List<DictionaryCommand> commandList) throws InterruptedException {
        for (DictionaryCommand dictionaryCommand : commandList) {
            receiveCommand(dictionaryCommand);
        }
    }
}
