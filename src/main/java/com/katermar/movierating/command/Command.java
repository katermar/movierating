package com.katermar.movierating.command;

import com.katermar.movierating.exception.BadRequestException;
import com.katermar.movierating.exception.CommandException;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by katermar on 12/30/2017.
 * <p>
 * FunctionalInterface which is used in CommandType as a value
 *
 * @see CommandType
 */
@FunctionalInterface
public interface Command {
    CommandResult executeAction(HttpServletRequest request) throws CommandException, BadRequestException;
}
