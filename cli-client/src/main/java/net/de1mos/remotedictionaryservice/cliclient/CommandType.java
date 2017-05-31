package net.de1mos.remotedictionaryservice.cliclient;

public enum CommandType {
    ADD("add"),
    REMOVE("delete"),
    GET("get");

    private final String cmdType;

    CommandType(String value) {
        this.cmdType = value;
    }

    public static CommandType getCommand(String value) {
        for (CommandType commandType : CommandType.values()) {
            if (commandType.cmdType.equals(value.toLowerCase())) {
                return commandType;
            }
        }
        return null;
    }
}
