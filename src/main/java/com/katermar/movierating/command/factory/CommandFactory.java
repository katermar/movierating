package com.katermar.movierating.command.factory;

import com.katermar.movierating.command.Command;
import com.katermar.movierating.command.CommandType;

import java.util.Optional;

/**
 * Created by katermar on 12/30/2017.
 * <p>
 * Class to identify command which comes from the request
 */
public class CommandFactory {
    private CommandFactory() {
    }

    /**
     * Method that identifies command from the CommandType enum or main-page command, if it isn't present
     *
     * @param commandType
     * @return identified command
     */
    public static Command identifyCommand(Optional<String> commandType) {
        return commandType
                .map(s -> CommandType.valueOf(revertIntoConstantName(s)).getCommand())
                .orElseGet(CommandType.MAIN_PAGE::getCommand);
    }

    /**
     * Method reverts command from the request view to constant name view
     * with a replacement of hyphens to underscores and converting to upper case
     *
     * @param commandName
     * @return converted command
     */
    public static String revertIntoConstantName(String commandName) {
        return commandName.replaceAll("-", "_").toUpperCase();
    }
}
