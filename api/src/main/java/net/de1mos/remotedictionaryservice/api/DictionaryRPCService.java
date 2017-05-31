package net.de1mos.remotedictionaryservice.api;

import java.util.List;

/**
 * API interface to RPC communication
 */
public interface DictionaryRPCService {

    void addTranslations(String word, List<String> translations);

    List<String> removeTranslations(String word, List<String> translations);

    List<String> getTranslations(String word);
}
