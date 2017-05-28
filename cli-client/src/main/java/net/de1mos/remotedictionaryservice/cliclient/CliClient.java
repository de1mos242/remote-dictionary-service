package net.de1mos.remotedictionaryservice.cliclient;

import net.de1mos.remotedictionaryservice.api.DictionaryRPCService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import org.apache.xmlrpc.client.util.ClientFactory;

import java.net.URL;

/**
 * Cli entry point for clients
 */
public class CliClient {
    private static final Logger logger = LogManager.getLogger(CliClient.class);

    public static void main(String[] args) throws Exception {
        String word = "hehey";
        String translation = "Привееет";
        logger.info("send add {} -> {}", word, translation);

        XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
        config.setServerURL(new URL("http://127.0.0.1:8080/xmlrpc"));
        config.setEnabledForExtensions(true);
        config.setConnectionTimeout(60 * 1000);
        config.setReplyTimeout(60 * 1000);
        XmlRpcClient client = new XmlRpcClient();
        client.setConfig(config);
        ClientFactory factory = new ClientFactory(client);
        DictionaryRPCService dictionaryRPCService = (DictionaryRPCService) factory.newInstance(DictionaryRPCService.class);

        dictionaryRPCService.addTranslation(word, translation);
        dictionaryRPCService.removeTranslation(word, translation);
        dictionaryRPCService.getTranslations(word);
    }

}
