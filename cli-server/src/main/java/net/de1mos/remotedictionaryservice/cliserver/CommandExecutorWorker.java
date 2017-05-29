package net.de1mos.remotedictionaryservice.cliserver;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Thread command executor worker
 */
public class CommandExecutorWorker {

    private static final Logger logger = LogManager.getLogger(CommandExecutorWorker.class);

    private CommandsExecutor commandsExecutor;

    private ExecutorService executorService;

    public CommandExecutorWorker(CommandsExecutor commandsExecutor) {
        this.commandsExecutor = commandsExecutor;
        this.executorService = Executors.newSingleThreadExecutor();
    }

    public void startListen() {
        executorService.submit(this::runExecution);
    }

    private void runExecution() {
        try {
            //noinspection InfiniteLoopStatement
            while (true) {
                commandsExecutor.ApplyNextCommand();
            }
        }
        catch (InterruptedException e) {
            logger.info("Received interrupt signal. Shutting down command execution thread.");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stopListen() {
        executorService.shutdownNow();
    }
}
