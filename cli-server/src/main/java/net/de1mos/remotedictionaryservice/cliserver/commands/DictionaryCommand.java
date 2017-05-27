package net.de1mos.remotedictionaryservice.cliserver.commands;

/**
 * Abstract command on dictionary translation
 */
public abstract class DictionaryCommand {
    protected String word;

    protected String translation;

    public DictionaryCommand(String word, String translation) {
        this.word = word;
        this.translation = translation;
    }

    public String getWord() {
        return word;
    }

    public String getTranslation() {
        return translation;
    }
}
