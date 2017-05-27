import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * Data access object to translation dictionary
 */
public class DictionaryRepository {

    private Map<String, Set<String>> internalDictionary;

    public DictionaryRepository() {
        final int initialCapacity = 16; // default value
        final float loadFactor = 0.75F; // default value
        final int concurrencyLevel = 1; // one writer, many readers
        this.internalDictionary = new ConcurrentHashMap<>(initialCapacity, loadFactor, concurrencyLevel);
    }

    /***
     * Add translation to dictionary if not exists
     * @param name name to translate, case insensitive
     * @param translation translation variant, case insensitive
     */
    public void addTranslation(String name, String translation) {
        final Set<String> bucket = getOrCreateBucket(name);
        addToTranslationsSet(bucket, translation);
    }

    /***
     * Get Set of translations for a given word
     * @param name name to translate, case insensitive
     * @return Set of translation strings in lower case
     */
    public Set<String> getTranslations(String name) {
        final Set<String> fromDictionary = getFromDictionary(name);
        if (fromDictionary != null) {
            return fromDictionary;
        }
        return Collections.emptySet();
    }

    /***
     * Remove translation from dictionary, remove whole word from dictionary if it was the last translation
     * @param name name of translation, case insensitive
     * @param translation translation variant, case insensitive
     */
    public void removeTranslation(String name, String translation) {
        final Set<String> translations = getFromDictionary(name);
        if (translations != null) {
            removeFromTranslationsSet(translations, translation);
            if (getFromDictionary(name).isEmpty()) {
                removeFromDictionary(name);
            }
        }
    }

    private Set<String> getOrCreateBucket(String name) {
        Set<String> bucket = getFromDictionary(name);
        if (bucket == null) {
            bucket = new ConcurrentSkipListSet<>();
            addToDictionary(name, bucket);
        }
        return bucket;
    }

    private Set<String> getFromDictionary(String name) {
        return internalDictionary.get(name.toLowerCase());
    }

    private void addToDictionary(String name, Set<String> bucket) {
        internalDictionary.put(name.toLowerCase(), bucket);
    }

    private void removeFromDictionary(String name) {
        internalDictionary.remove(name.toLowerCase());
    }

    private void addToTranslationsSet(Set<String> bucket, String translation) {
        bucket.add(translation.toLowerCase());
    }

    private void removeFromTranslationsSet(Set<String> bucket, String translation) {
        bucket.remove(translation.toLowerCase());
    }
}
