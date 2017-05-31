package net.de1mos.remotedictionaryservice.cliserver.exceptions;

/**
 * This error is thrown if commands queue is exceed its limit.
 */
public class CommandQueueSizeLimitExceedException extends RuntimeException {
    public CommandQueueSizeLimitExceedException(String s) {
        super(s);
    }
}
