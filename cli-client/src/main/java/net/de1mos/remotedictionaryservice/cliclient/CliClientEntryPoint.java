package net.de1mos.remotedictionaryservice.cliclient;

import net.de1mos.remotedictionaryservice.api.DictionaryRPCService;

import java.lang.reflect.UndeclaredThrowableException;
import java.util.Arrays;
import java.util.List;

/**
 * Entry point to client application
 */
public class CliClientEntryPoint {

    public static void main(String[] args) {
        List<String> argsList = Arrays.asList(args);

        int argsSize = argsList.size();
        if (argsSize < 2) {
            printHelp();
            return;
        }

        CliAddressParser addressParser = new CliAddressParser(argsList.get(0));
        if (!addressParser.isValid()) {
            printHelp();
            return;
        }

        CliCommandParser commandParser = new CliCommandParser(argsList.subList(1, argsList.size()));
        if (!commandParser.isValid()) {
            printHelp();
            return;
        }

        try {
            CliClient cliClient = new CliClient(addressParser.getAddressPart(), addressParser.getPort());
            DictionaryRPCService dictionaryRPCService = cliClient.prepareRpcClient();

            switch (commandParser.getCommandType()) {
                case ADD:
                    sendAddTranslations(dictionaryRPCService,
                            commandParser.getWord(),
                            commandParser.getAddedTranslations());
                    break;
                case REMOVE:
                    sendRemoveTranslations(dictionaryRPCService,
                            commandParser.getWord(),
                            commandParser.getRemoveTranslations());
                    break;
                case GET:
                    sendGetTranslation(dictionaryRPCService,
                            commandParser.getWord());
                    break;
            }
        } catch (UndeclaredThrowableException e) {
            printLine(String.format("Произошла ошибка при взимодействии с сервером: %s",
                    e.getCause().getMessage()));
        } catch (Exception e) {
            printLine(e.getClass().getCanonicalName());
            printLine(String.format("Произошла ошибка при взимодействии с сервером: %s", e.getMessage()));
        }
    }

    private static void sendGetTranslation(DictionaryRPCService dictionaryRPCService, String word) {
        final List<String> translations = dictionaryRPCService.getTranslations(word);
        if (!translations.isEmpty()) {
            translations.forEach(CliClientEntryPoint::printLine);
        } else {
            printLine("<слово отсутвует в словаре>");
        }
    }

    private static void sendRemoveTranslations(DictionaryRPCService dictionaryRPCService,
                                               String word,
                                               List<String> removeTranslations) {
        List<String> notRemoved = dictionaryRPCService.removeTranslations(word, removeTranslations);
        if (notRemoved.isEmpty()) {
            printLine("<значения слова успешно удалены>");
        } else {
            notRemoved.forEach(notRemovedTranslation -> {
                printLine(String.format("<%s/%s отсутвует в словаре>", word, notRemovedTranslation));
            });
        }
    }

    private static void sendAddTranslations(DictionaryRPCService dictionaryRPCService,
                                            String word,
                                            List<String> addedTranslations) {
        dictionaryRPCService.addTranslations(word, addedTranslations);
        printLine("<значения слова успешно добавлены>");
    }

    private static void printHelp() {
        printLine("Ошибка в синтаксисе команды\n");
        printLine("Использование:");
        printLine("\tадрес[:порт] add слово перевод [перевод2 ...]");
        printLine("\tадрес[:порт] delete слово перевод [перевод2 ...]");
        printLine("\tадрес[:порт] get слово перевод");
        printLine("");
        printLine("адрес[:порт] - ip или имя хоста (localhost, 127.0.0.1)");
        printLine("\t\tпорт - порт сервера (1-65535). По умолчанию 80");
        printLine("\t\tПример: localhost:8080");
        printLine("add - добавляет в словарь перевод(ы) слова");
        printLine("delete - удаляет из словаря перевод(ы) слова");
        printLine("get - получить список переводов слова");

    }

    private static void printLine(String s) {
        System.out.println(s);
    }
}
