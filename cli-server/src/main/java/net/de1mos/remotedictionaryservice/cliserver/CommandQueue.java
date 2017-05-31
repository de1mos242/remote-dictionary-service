package net.de1mos.remotedictionaryservice.cliserver;

import net.de1mos.remotedictionaryservice.cliserver.commands.DictionaryCommand;
import net.de1mos.remotedictionaryservice.cliserver.exceptions.CommandQueueSizeLimitExceedException;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Commands queue to modify dictionary repository
 */
public class CommandQueue {

    private BlockingQueue<DictionaryCommand> internalQueue;

    private final int maxQueueSize;

    public CommandQueue(int maxQueueSize) {
        this.internalQueue = new LinkedBlockingQueue<>();
        this.maxQueueSize = maxQueueSize;
    }

    /***
     * Add a command to queue
     * @param command a command to execute
     * @throws InterruptedException while put into blocking queue
     */
    public void addToQueue(DictionaryCommand command) throws InterruptedException {
        final int currentSize = internalQueue.size();
        if (currentSize >= maxQueueSize) {
            throw new CommandQueueSizeLimitExceedException(
                    String.format("Command queue size of %s exceed with %s",
                        maxQueueSize,
                        currentSize));
        }

        internalQueue.put(command);
    }

    /***
     * Blocking take an element from queue
     * @return next command or wait when it will appear
     * @throws InterruptedException while take from blocking queue
     */
    public DictionaryCommand getNextCommand() throws InterruptedException {
        return internalQueue.take();
    }
}
