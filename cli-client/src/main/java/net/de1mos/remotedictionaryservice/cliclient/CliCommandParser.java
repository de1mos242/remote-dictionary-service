package net.de1mos.remotedictionaryservice.cliclient;

import java.util.List;

/**
 * Commands reader
 */
public class CliCommandParser {
    private List<String> cmdArgs;

    private CommandType commandType;

    private boolean isValid;

    private String word;

    private List<String> addedTranslations;

    private List<String> removeTranslations;

    public CliCommandParser(List<String> cmdArgs) {
        this.cmdArgs = cmdArgs;

        parse();
    }

    public boolean isValid() {
        return isValid;
    }

    public CommandType getCommandType() {
        return commandType;
    }

    public String getWord() {
        return word;
    }

    public List<String> getAddedTranslations() {
        return addedTranslations;
    }

    public List<String> getRemoveTranslations() {
        return removeTranslations;
    }

    private void parse() {
        isValid = checkIsValid();
        if (!isValid) {
            return;
        }

        commandType = CommandType.getCommand(cmdArgs.get(0));
        if (commandType == null) {
            throw new IllegalStateException("Null value of cmd type, while state is valid");
        }
        word = parseWord();
        switch (commandType) {
            case ADD:
                addedTranslations = cmdArgs.subList(2, cmdArgs.size());
                break;
            case REMOVE:
                removeTranslations = cmdArgs.subList(2, cmdArgs.size());
                break;
        }
    }

    private String parseWord() {
        return cmdArgs.get(1);
    }

    private boolean checkIsValid() {
        if (cmdArgs.size() == 0) {
            return false;
        }
        CommandType cmdType = CommandType.getCommand(cmdArgs.get(0));
        if (cmdType == null) {
            return false;
        }
        switch (cmdType) {
            case ADD:
                if (cmdArgs.size() < 3) {
                    return false;
                }
                break;
            case REMOVE:
                if (cmdArgs.size() < 3) {
                    return false;
                }
                break;
            case GET:
                if (cmdArgs.size() != 2) {
                    return false;
                }
                break;
        }
        return true;
    }

}
