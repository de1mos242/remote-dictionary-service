package net.de1mos.remotedictionaryservice.cliclient;

import net.de1mos.remotedictionaryservice.api.DictionaryRPCService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import org.apache.xmlrpc.client.util.ClientFactory;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Cli entry point for clients
 */
public class CliClient {
    private static final Logger logger = LogManager.getLogger(CliClient.class);

    private String address;

    private int port;

    public CliClient(String address, int port) {
        this.address = address;
        this.port = port;
    }

    public DictionaryRPCService prepareRpcClient() throws MalformedURLException {
        XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
        config.setServerURL(new URL(String.format("http://%s:%s/xmlrpc", address, port)));
        config.setEnabledForExtensions(true);
        config.setConnectionTimeout(60 * 1000);
        config.setReplyTimeout(60 * 1000);
        XmlRpcClient client = new XmlRpcClient();
        client.setConfig(config);
        ClientFactory factory = new ClientFactory(client);
        return (DictionaryRPCService) factory.newInstance(DictionaryRPCService.class);
    }

}
