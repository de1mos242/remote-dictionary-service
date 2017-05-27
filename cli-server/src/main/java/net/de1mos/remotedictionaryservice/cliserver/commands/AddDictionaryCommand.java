package net.de1mos.remotedictionaryservice.cliserver.commands;

/**
 * Add translation to dictionary command
 */
public class AddDictionaryCommand extends DictionaryCommand {

    public AddDictionaryCommand(String word, String translation) {
        super(word, translation);
    }
}
