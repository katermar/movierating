package com.katermar.movierating.command.factory;

import com.katermar.movierating.command.Command;
import com.katermar.movierating.command.CommandType;

import java.util.Optional;

/**
 * Created by katermar on 12/30/2017.
 */
public class CommandFactory {
    public static Command identifyCommand(Optional<String> commandType) {
        Command identifiedCommand;
        identifiedCommand = commandType
                .map(s -> CommandType.valueOf(revertIntoConstantName(s)).getCommand())
                .orElseGet(CommandType.MAIN_PAGE::getCommand);
        return identifiedCommand;
    }

    private static String revertIntoConstantName(String commandName) {
        return commandName.replaceAll("-", "_").toUpperCase();
    }
}
