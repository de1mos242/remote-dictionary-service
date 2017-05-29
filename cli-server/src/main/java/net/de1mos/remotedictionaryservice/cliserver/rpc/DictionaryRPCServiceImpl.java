package net.de1mos.remotedictionaryservice.cliserver.rpc;

import net.de1mos.remotedictionaryservice.api.DictionaryRPCService;
import net.de1mos.remotedictionaryservice.cliserver.CommandsReceiver;
import net.de1mos.remotedictionaryservice.cliserver.DictionaryRepository;
import net.de1mos.remotedictionaryservice.cliserver.commands.AddDictionaryCommand;
import net.de1mos.remotedictionaryservice.cliserver.commands.RemoveDictionaryCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of dictionary API Interface
 */
public class DictionaryRPCServiceImpl implements DictionaryRPCService {
    private static final Logger logger = LogManager.getLogger("DictionaryRPCServiceImpl");

    private CommandsReceiver receiver;

    private DictionaryRepository dictionaryRepository;

    public DictionaryRPCServiceImpl(CommandsReceiver receiver, DictionaryRepository dictionaryRepository) {
        this.receiver = receiver;
        this.dictionaryRepository = dictionaryRepository;
    }

    @Override
    public void addTranslation(String word, String translation) {
        logger.info("Receive add translation: {} -> {}", word, translation);
        try {
            receiver.receiveCommand(new AddDictionaryCommand(word, translation));
        } catch (InterruptedException e) {
            logger.info("Interrupt while receive add translation");
        }
    }

    @Override
    public void removeTranslation(String word, String translation) {
        logger.info("Receive remove translation: {} -> {}", word, translation);
        try {
            receiver.receiveCommand(new RemoveDictionaryCommand(word, translation));
        } catch (InterruptedException e) {
            logger.info("Interrupt while receive remove translation");
        }
    }

    @Override
    public List<String> getTranslations(String word) {
        logger.info("Receive request for translations: {}", word);
        return new ArrayList<>(dictionaryRepository.getTranslations(word));
    }
}
