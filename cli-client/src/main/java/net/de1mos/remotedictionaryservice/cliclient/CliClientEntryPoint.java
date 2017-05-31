package net.de1mos.remotedictionaryservice.cliclient;

import net.de1mos.remotedictionaryservice.api.DictionaryRPCService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Entry point to client application
 */
public class CliClientEntryPoint {

    private static final Logger logger = LogManager.getLogger(CliClientEntryPoint.class);

    public static void main(String[] args) throws Exception {
        String word = "hehey";
        String translation = "Привееет";
        logger.info("send add {} -> {}", word, translation);


        CliClient cliClient = new CliClient("127.0.0.1", 8080);

        int maxIterations = 1;
        logger.info(args);
        if (args.length > 0) {

            maxIterations = Integer.parseInt(args[0]);
        }

        for (int i = 0; i < maxIterations; i++) {
            DictionaryRPCService dictionaryRPCService = cliClient.prepareRpcClient();
            dictionaryRPCService.addTranslations(word, Collections.singletonList(translation));
            final List<String> translations = dictionaryRPCService.getTranslations(word);
            logger.info("translates for {} is {}", word, String.join(", ", translations));
            dictionaryRPCService.removeTranslation(word, translation);
            final List<String> translationsEmtpy = dictionaryRPCService.getTranslations(word);
            logger.info("emtpry translates for {} is {}", word, String.join(", ", translationsEmtpy));
        }
    }
}
