package net.de1mos.remotedictionaryservice.cliserver.rpc;

import net.de1mos.remotedictionaryservice.api.DictionaryRPCService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * Implementation of dictionary API Interface
 */
public class DictionaryRPCServiceImpl implements DictionaryRPCService {
    private static final Logger logger = LogManager.getLogger("DictionaryRPCServiceImpl");

    @Override
    public void addTranslation(String word, String translation) {
        logger.info("Receive add translation: {} -> {}", word, translation);
    }

    @Override
    public void removeTranslation(String word, String translation) {
        logger.info("Receive remove translation: {} -> {}", word, translation);
    }

    @Override
    public List<String> getTranslations(String word) {
        logger.info("Receive request for translations: {}", word);
        return null;
    }
}
