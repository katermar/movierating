package com.katermar.movierating.controller;

import com.katermar.movierating.command.Command;
import com.katermar.movierating.command.CommandResult;
import com.katermar.movierating.command.factory.CommandFactory;
import com.katermar.movierating.exception.BadRequestException;
import com.katermar.movierating.exception.CommandException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

import static com.katermar.movierating.command.CommandResult.ResponseType.FORWARD;
import static com.katermar.movierating.command.CommandResult.ResponseType.REDIRECT;

/**
 * Created by katermar on 12/30/2017.
 */
@WebServlet(name = "Controller", urlPatterns = {"/controller"})
public class Controller extends HttpServlet {
    private static final Logger LOGGER = LogManager.getLogger(Controller.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Optional<String> requestCommand = Optional.ofNullable(request.getParameter("command"));
            Command command = CommandFactory.identifyCommand(requestCommand);
            CommandResult commandResult = command.executeAction(request);
            String page = commandResult.getPage();
            if (commandResult.getResponseType().equals(REDIRECT)) {
                response.sendRedirect(request.getContextPath() + page);
            } else if (commandResult.getResponseType().equals(FORWARD)) {
                RequestDispatcher requestDispatcher = request.getRequestDispatcher(page);
                requestDispatcher.forward(request, response);
            } else {
                response.sendError(commandResult.getErrorCode(), commandResult.getErrorMessage());
            }
        } catch (BadRequestException e) {
            LOGGER.warn(e.getMessage());
            response.sendError(400, "Bad request parameters");
        } catch (IOException | CommandException e) {
            LOGGER.warn(e.getMessage());
            throw new ServletException(e);
        }
    }
}
