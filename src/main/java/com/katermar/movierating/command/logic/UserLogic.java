package com.katermar.movierating.command.logic;

import com.katermar.movierating.command.CommandResult;
import com.katermar.movierating.config.Attribute;
import com.katermar.movierating.config.Message;
import com.katermar.movierating.config.PagePath;
import com.katermar.movierating.entity.User;
import com.katermar.movierating.exception.ServiceException;
import com.katermar.movierating.service.AuthSecurityService;
import com.katermar.movierating.service.RegisterService;
import com.katermar.movierating.service.UserService;
import com.katermar.movierating.service.impl.AuthSecurityServiceImpl;
import com.katermar.movierating.service.impl.RatingService;
import com.katermar.movierating.service.impl.RegisterServiceImpl;
import com.katermar.movierating.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * Created by katermar on 12/31/2017.
 */
public class UserLogic {
    private static final Logger LOGGER = LogManager.getLogger(UserLogic.class);

    public CommandResult confirmEmail(HttpServletRequest request) {
        String login = request.getParameter(Attribute.USER);
        RegisterService registerService = new RegisterServiceImpl();
        UserService userService = new UserServiceImpl();
        try {
            registerService.confirmEmail(userService.findByLogin(login));
        } catch (ServiceException e) {
            LOGGER.warn(e.getMessage());// todo
        }
        return new CommandResult(CommandResult.ResponseType.FORWARD, PagePath.MAIN);
    }

    public CommandResult logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute(Attribute.USER);
            session.invalidate();
        }
        return new CommandResult(CommandResult.ResponseType.FORWARD, PagePath.MAIN);
    }

    public CommandResult updatePassword(HttpServletRequest request) {
        UserService userService = new UserServiceImpl();
        try {
            String login = request.getParameter(Attribute.USERNAME);
            String password = request.getParameter(Attribute.PASSWORD);
            userService.updatePassword(password, login);
        } catch (ServiceException e) {
            LOGGER.warn(e.getMessage());
        }
        return new CommandResult(CommandResult.ResponseType.FORWARD, PagePath.MAIN);
    }

    public CommandResult login(HttpServletRequest request) {
        AuthSecurityService authSecurityService = new AuthSecurityServiceImpl();

        String login = request.getParameter(Attribute.USERNAME);
        String password = request.getParameter(Attribute.PASSWORD);

        CommandResult commandResult = null;
        User user = null;
        try {
            user = authSecurityService.checkUserCredentials(login, password);
        } catch (ServiceException e) {
            LOGGER.warn(e.getMessage());
        }

        if (user == null || user.isBaned()) {
            request.setAttribute(Attribute.LOGIN_ERROR, Message.LOGIN_ERROR);
        } else {
            HttpSession session = request.getSession();
            session.setAttribute(Attribute.USER, user);
            session.setMaxInactiveInterval(500);
            commandResult = new CommandResult(CommandResult.ResponseType.REDIRECT, request.getHeader("Referer"));
        }
        return commandResult;
    }

    public CommandResult goToProfilePage(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            // todo error
        }
        UserService userService = new UserServiceImpl();
        User currentUser = (User) session.getAttribute(Attribute.USER);
        RatingService ratingService = new RatingService();
        try {
            Map<String, Long> ratingMap = ratingService.getRatingMapByUser(currentUser.getId());
//            throw new NullPointerException(ratingMap.toString());
            request.setAttribute("total", ratingMap.values().stream().reduce(0L, Long::sum));
            request.setAttribute("ratings", ratingMap);
        } catch (ServiceException e) {
            LOGGER.warn(e.getMessage()); //todo
        }

        return new CommandResult(CommandResult.ResponseType.FORWARD, PagePath.PROFILE);
    }
}