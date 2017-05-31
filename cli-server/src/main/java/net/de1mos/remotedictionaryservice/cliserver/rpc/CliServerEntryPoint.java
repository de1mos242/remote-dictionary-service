package net.de1mos.remotedictionaryservice.cliserver.rpc;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Entry point to console application
 */
public class CliServerEntryPoint {

    private static final Logger logger = LogManager.getLogger(CliServerEntryPoint.class);

    public static void main(String[] args) {
        if (args.length > 1) {
            printHelp();
            return;
        }

        int port = 80;
        if (args.length == 1) {

            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                printHelp();
                return;
            }
        }

        CliServer server = new CliServer(port);

        try {
            server.startWebServer();
        } catch (Exception e) {
            logger.error(String.format("Не удалось запустить сервер по на порту %s: %s", port, e.getMessage()));
            server.shutdownServer();
            return;
        }

        try {
            Thread.sleep(Long.MAX_VALUE);
        }
        catch (InterruptedException e) {
            server.shutdownServer();
        }
    }

    private static void printHelp() {
        logger.info("Ошибка в синтаксисе команды\n");
        logger.info("Использование:");
        logger.info("[порт] - порт сервера (1-65535), по умолчанию 80");
    }
}
