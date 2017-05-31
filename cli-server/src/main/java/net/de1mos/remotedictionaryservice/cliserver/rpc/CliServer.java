package net.de1mos.remotedictionaryservice.cliserver.rpc;

import net.de1mos.remotedictionaryservice.api.DictionaryRPCService;
import net.de1mos.remotedictionaryservice.cliserver.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.server.PropertyHandlerMapping;
import org.apache.xmlrpc.server.XmlRpcServer;
import org.apache.xmlrpc.server.XmlRpcServerConfigImpl;
import org.apache.xmlrpc.webserver.WebServer;

import java.io.IOException;

/**
 * CLI server to handle remote prc requests
 */
public class CliServer {

    private static final Logger logger = LogManager.getLogger(CliServer.class);
    public static final int DEFAULT_MAX_QUEUE_SIZE = 1000;
    private int port;
    private WebServer webServer;
    private CommandExecutorWorker executionWorker;
    private DictionaryRepository dictionaryRepository;
    private int maxQueueSize;

    public CliServer(int port) {
        this.port = port;
        this.dictionaryRepository = new DictionaryRepository();
        this.maxQueueSize = DEFAULT_MAX_QUEUE_SIZE;
    }

    public void shutdownServer() {
        logger.info("Shutting down server");
        if (webServer != null) {
            webServer.shutdown();
        }
        if (executionWorker != null) {
            executionWorker.stopListen();
        }
        logger.info("Server stopped");
    }

    public void startWebServer() throws XmlRpcException, IOException {
        final CommandQueue commandQueue = new CommandQueue(maxQueueSize);
        DictionaryRPCService service = getDictionaryRPCService(commandQueue);

        startExecutionWorker(commandQueue);

        webServer = new WebServer(port);
        XmlRpcServer xmlRpcServer = webServer.getXmlRpcServer();
        PropertyHandlerMapping phm = new PropertyHandlerMapping();

        phm.setRequestProcessorFactoryFactory(new DictionaryRECServiceProcessorFactoryFactory(service));
        phm.setVoidMethodEnabled(true);
        phm.addHandler(DictionaryRPCService.class.getName(), DictionaryRPCService.class);
        xmlRpcServer.setHandlerMapping(phm);

        XmlRpcServerConfigImpl serverConfig = (XmlRpcServerConfigImpl) xmlRpcServer.getConfig();
        serverConfig.setEnabledForExtensions(true);
        serverConfig.setContentLengthOptional(false);

        logger.info("Starting server on {} port...", port);

        webServer.start();
        logger.info("Server successfully started on {} port", port);
    }

    private void startExecutionWorker(CommandQueue commandQueue) {
        final CommandsExecutor commandsExecutor = new CommandsExecutor(dictionaryRepository, commandQueue);
        executionWorker = new CommandExecutorWorker(commandsExecutor);
        executionWorker.startListen();
    }

    private DictionaryRPCService getDictionaryRPCService(CommandQueue commandQueue) {
        final CommandsReceiver commandsReceiver = new CommandsReceiver(commandQueue);
        return new DictionaryRPCServiceImpl(commandsReceiver, dictionaryRepository);
    }
}
