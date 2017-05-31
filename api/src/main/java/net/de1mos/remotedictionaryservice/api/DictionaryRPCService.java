package net.de1mos.remotedictionaryservice.api;

import java.util.List;

/**
 * API interface to RPC communication
 */
public interface DictionaryRPCService {

    void addTranslations(String word, List<String> translations);

    void removeTranslation(String word, String translation);

    List<String> getTranslations(String word);
}
