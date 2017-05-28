package net.de1mos.remotedictionaryservice.cliserver.rpc;

import net.de1mos.remotedictionaryservice.api.DictionaryRPCService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.xmlrpc.server.PropertyHandlerMapping;
import org.apache.xmlrpc.server.XmlRpcServer;
import org.apache.xmlrpc.server.XmlRpcServerConfigImpl;
import org.apache.xmlrpc.webserver.WebServer;

import java.io.IOException;

/**
 * CLI server to handle remote prc requests
 */
public class CliServer {

    private static final Logger logger = LogManager.getLogger("CliServer");

    public static void main(String[] args) throws Exception {
        final int port = 8080;
        WebServer webServer = new WebServer(port);
        XmlRpcServer xmlRpcServer = webServer.getXmlRpcServer();
        PropertyHandlerMapping phm = new PropertyHandlerMapping();

        DictionaryRPCService service = new DictionaryRPCServiceImpl();
        phm.setRequestProcessorFactoryFactory(new DictonaryRPCServiceProcessorFactoryFactory(service));
        phm.setVoidMethodEnabled(true);
        phm.addHandler(DictionaryRPCService.class.getName(), DictionaryRPCService.class);
        xmlRpcServer.setHandlerMapping(phm);

        XmlRpcServerConfigImpl serverConfig = (XmlRpcServerConfigImpl) xmlRpcServer.getConfig();
        serverConfig.setEnabledForExtensions(true);
        serverConfig.setContentLengthOptional(false);

        logger.info("Starting server on {} port...", port);

        webServer.start();
        try {
            Thread.sleep(Long.MAX_VALUE);
        }
        catch (InterruptedException e) {
            logger.info("Shutting down server");
            webServer.shutdown();
        }
        logger.info("Server stopped");
    }
}
