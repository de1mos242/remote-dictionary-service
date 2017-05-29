package net.de1mos.remotedictionaryservice.cliserver;

import net.de1mos.remotedictionaryservice.cliserver.commands.DictionaryCommand;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Commands queue to modify dictionary repository
 */
public class CommandQueue {

    private BlockingQueue<DictionaryCommand> internalQueue;

    public CommandQueue() {
        this.internalQueue = new LinkedBlockingQueue<>();
    }

    /***
     * Add a command to queue
     * @param command a command to execute
     * @throws InterruptedException while put into blocking queue
     */
    public void addToQueue(DictionaryCommand command) throws InterruptedException {
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
