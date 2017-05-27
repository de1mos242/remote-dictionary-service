package net.de1mos.remotedictionaryservice.cliserver;

import net.de1mos.remotedictionaryservice.cliserver.commands.DictionaryCommand;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * Commands queue to modify dictionary repository
 */
public class CommandQueue {

    private Queue<DictionaryCommand> internalQueue;

    public CommandQueue() {
        this.internalQueue = new ConcurrentLinkedDeque<>();
    }

    public void addToQueue(DictionaryCommand command) {
        internalQueue.add(command);
    }

    public DictionaryCommand getNextCommand() {
        return internalQueue.poll();
    }
}
