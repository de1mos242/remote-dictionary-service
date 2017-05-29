package net.de1mos.remotedictionaryservice.cliserver.rpc;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Entry point to console application
 */
public class CliServerEntryPoint {

    private static final Logger logger = LogManager.getLogger(CliServerEntryPoint.class);

    public static void main(String[] args) throws Exception {
        final int port = 8080;
        CliServer server = new CliServer(port);
        server.startWebServer();
        try {
            Thread.sleep(Long.MAX_VALUE);
        }
        catch (InterruptedException e) {
            server.shutdownServer();
        }
    }
}
