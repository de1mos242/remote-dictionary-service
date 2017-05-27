package net.de1mos.remotedictionaryservice.cliserver.commands;

/**
 * Remove translation from dictionary command
 */
public class RemoveDictionaryCommand extends DictionaryCommand {

    public RemoveDictionaryCommand(String word, String translation) {

        super(word, translation);
    }
}
