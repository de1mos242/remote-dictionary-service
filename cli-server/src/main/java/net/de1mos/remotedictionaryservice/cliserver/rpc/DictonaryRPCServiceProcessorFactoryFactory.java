package net.de1mos.remotedictionaryservice.cliserver.rpc;

import net.de1mos.remotedictionaryservice.api.DictionaryRPCService;
import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.XmlRpcRequest;
import org.apache.xmlrpc.server.RequestProcessorFactoryFactory;

/**
 * Factory to store one instance of Dictionary service implementation
 */
public class DictonaryRPCServiceProcessorFactoryFactory
        implements RequestProcessorFactoryFactory {
    private final DictionaryRPCService service;

    private final RequestProcessorFactoryFactory.RequestProcessorFactory factory = new DictionaryRPCServiceImplProcessorFactory();

    public DictonaryRPCServiceProcessorFactoryFactory(DictionaryRPCService service) {
        this.service = service;
    }

    @Override
    public RequestProcessorFactory getRequestProcessorFactory(Class pClass) throws XmlRpcException {
        return factory;
    }

    private class DictionaryRPCServiceImplProcessorFactory implements RequestProcessorFactoryFactory.RequestProcessorFactory {
        @Override
        public Object getRequestProcessor(XmlRpcRequest pRequest) throws XmlRpcException {
            return service;
        }
    }
}
