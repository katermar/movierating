package com.katermar.movierating.command.logic;

import com.katermar.movierating.command.CommandResult;
import com.katermar.movierating.config.Attribute;
import com.katermar.movierating.config.PagePath;
import com.katermar.movierating.entity.Rating;
import com.katermar.movierating.entity.Review;
import com.katermar.movierating.entity.User;
import com.katermar.movierating.exception.CommandException;
import com.katermar.movierating.exception.ServiceException;
import com.katermar.movierating.service.AuthSecurityService;
import com.katermar.movierating.service.EmailSenderService;
import com.katermar.movierating.service.RegisterService;
import com.katermar.movierating.service.UserService;
import com.katermar.movierating.service.impl.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jasypt.util.text.BasicTextEncryptor;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Created by katermar on 12/31/2017.
 */
public class UserLogic {
    private static final Logger LOGGER = LogManager.getLogger(UserLogic.class);

    public CommandResult confirmEmail(HttpServletRequest request) throws CommandException {
        String login = request.getParameter(Attribute.USER).replace(" ", "+");
        System.out.println(login);
        RegisterService registerService = new RegisterServiceImpl();
        BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
        textEncryptor.setPassword(ResourceBundle.getBundle("project").getString("encryption.password"));
        UserService userService = new UserServiceImpl();
        try {
            HttpSession session = request.getSession(false);
            if (session != null) {
                User currentUser = (User) session.getAttribute(Attribute.USER);
                if (currentUser.getLogin().equals(textEncryptor.decrypt(login))) {
                    currentUser.setStatus(User.UserStatus.UNBANED);
                    registerService.confirmEmail(userService.getByLogin(textEncryptor.decrypt(login)));
                }
                session.removeAttribute(Attribute.USER);
                session.setAttribute(Attribute.USER, currentUser);
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return new CommandResult(CommandResult.ResponseType.FORWARD, PagePath.PROFILE);
    }

    public CommandResult logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute(Attribute.USER);
            session.invalidate();
        }
        return new CommandResult(CommandResult.ResponseType.REDIRECT, PagePath.REDIRECT_MAIN);
    }

    public CommandResult login(HttpServletRequest request) throws CommandException {
        AuthSecurityService authSecurityService = new AuthSecurityServiceImpl();

        String login = request.getParameter(Attribute.USERNAME);
        String password = request.getParameter(Attribute.PASSWORD);

        User user = null;
        try {
            user = authSecurityService.checkUserCredentials(login, password);
            if (user == null || user.isBaned()) {
                request.setAttribute(Attribute.LOGIN_ERROR, "User credentials are invalid. Try again!");
            } else {
                HttpSession session = request.getSession();
                session.setAttribute(Attribute.USER, user);
                session.setMaxInactiveInterval(500);
            }
        } catch (ServiceException e) {
            throw new CommandException();
        }
        return new CommandResult(CommandResult.ResponseType.FORWARD, PagePath.MAIN);
    }

    public CommandResult goToProfilePage(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession(false);
        if (session == null) {
            throw new CommandException("Session is over.");
        }
        User currentUser = (User) session.getAttribute(Attribute.USER);
        RatingService ratingService = new RatingService();
        try {
            Map<String, Long> ratingMap = ratingService.getRatingMapByUser(currentUser.getId());
            request.setAttribute("total", ratingMap.values().stream().reduce(0L, Long::sum));
            request.setAttribute("ratings", ratingMap);
            request.setAttribute("userAverageRating", ratingService.getAverageRatingByUser(currentUser.getId()));
        } catch (ServiceException e) {
            throw new CommandException(e);
        }

        return new CommandResult(CommandResult.ResponseType.FORWARD, PagePath.PROFILE);
    }

    public CommandResult leaveReview(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession(false);
        if (session == null) {
            throw new CommandException("Session is over.");
        }
        User currentUser = (User) session.getAttribute(Attribute.USER);
        ReviewService reviewService = new ReviewService();

        Review review = new Review();
        review.setUser(currentUser);
        review.setIduser(currentUser.getId());
        review.setText(request.getParameter("review"));
        review.setIdfilm(Integer.parseInt(request.getParameter("id")));
        try {
            reviewService.addReview(review);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return new CommandResult(CommandResult.ResponseType.REDIRECT, request.getHeader("Referer"));
    }

    public CommandResult addRating(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession(false);
        if (session == null) {
            throw new CommandException("Session is over.");
        }
        User currentUser = (User) session.getAttribute(Attribute.USER);
        Rating rating = new Rating();
        rating.setIdfilm(Integer.parseInt(request.getParameter("id")));
        rating.setIduser(currentUser.getId());
        rating.setIsSeen(true);
        rating.setRatingAmount(Integer.parseInt(request.getParameter("rating")));
        RatingService ratingService = new RatingService();
        UserService userService = new UserServiceImpl();
        try {
            ratingService.addRating(rating);
            userService.updateLevel(currentUser, rating);
        } catch (ServiceException e) {
            LOGGER.warn(e.getMessage());
        }
        return new CommandResult(CommandResult.ResponseType.REDIRECT, request.getHeader("Referer"));
    }

    public CommandResult updateWatched(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession(false);
        if (session == null) {
            throw new CommandException("Session isn't opened.");
        }
        User currentUser = (User) session.getAttribute(Attribute.USER);
        RatingService ratingService = new RatingService();
        int filmId = Integer.parseInt(request.getParameter("id"));
        Rating rating;
        try {
            rating = ratingService.getRatingByUserAndFilm(currentUser.getId(), filmId);
            rating.setIsSeen(!rating.getIsSeen());
            ratingService.addRating(rating);
        } catch (ServiceException e) {
            LOGGER.warn(e.getMessage());
        }
        return new CommandResult(CommandResult.ResponseType.REDIRECT, request.getHeader("Referer"));
    }

    public CommandResult setAvatar(HttpServletRequest request) throws CommandException {
        try {
            String imageURL = request.getParameter("file");
            LOGGER.warn(imageURL);
            if (!imageURL.endsWith("svg")) {
                ImageIO.setUseCache(false);
                BufferedImage image = ImageIO.read(new URL(imageURL));
                if (image.getHeight() > 300 || image.getWidth() > 300) {
                    throw new CommandException("Invalid image size! Try again please!");
                }
            }
            UserService userService = new UserServiceImpl();
            User currentUser = (User) request.getSession(false).getAttribute("user");
            currentUser.setAvatar(imageURL);
            userService.addUser(currentUser);
        } catch (IOException | ServiceException e) {
            LOGGER.warn(e.getMessage());
            throw new CommandException(e);
        }
        return new CommandResult(CommandResult.ResponseType.REDIRECT, request.getHeader("Referer"));
    }

    public CommandResult sendEmail(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession(false);
        if (session == null) {
            throw new CommandException("Session is over.");
        }
        User currentUser = (User) session.getAttribute(Attribute.USER);
        currentUser.setEmail(request.getParameter("email"));
        UserService userService = new UserServiceImpl();
        try {
            userService.addUser(currentUser);
            EmailSenderService emailSenderService = new EmailSenderServiceImpl();
            emailSenderService.sendConfirmationMail(currentUser.getLogin(), currentUser.getEmail());
        } catch (ServiceException e) {
            LOGGER.error(e.getMessage());
            throw new CommandException(e);
        }
        return new CommandResult(CommandResult.ResponseType.REDIRECT, request.getHeader("Referer"));
    }

    public CommandResult editProfile(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession(false);
        if (session == null) {
            throw new CommandException("Session is over.");
        }
        User currentUser = (User) session.getAttribute(Attribute.USER);

        try {
            UserService userService = new UserServiceImpl();
            String email = request.getParameter("email");
            if (!email.equals(currentUser.getEmail())) {
                userService.updateStatus(User.UserStatus.UNCONFIRMED, currentUser.getLogin());
            }
            currentUser.setEmail(email);
            String birthday = request.getParameter("birthday").trim();
            if (!birthday.isEmpty()) {
                currentUser.setDateOfBirth(Date.valueOf(LocalDate.parse(birthday, DateTimeFormatter.ISO_DATE)));
            }
            currentUser.setRealName(request.getParameter("realname"));
            userService.addUser(currentUser);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return new CommandResult(CommandResult.ResponseType.REDIRECT, request.getHeader("Referer"));
    }

    public CommandResult sendNewPassword(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession(false);
        if (session == null) {
            throw new CommandException("Session is over.");
        }
        User currentUser = (User) session.getAttribute(Attribute.USER);
        if (currentUser.getStatus().equals(User.UserStatus.UNCONFIRMED)) {
            throw new CommandException("Users' e-mail is unconfirmed.");
        }
        String newPassword = RandomStringUtils.randomAscii(12);
        UserService userService = new UserServiceImpl();
        try {
            new EmailSenderServiceImpl().sendNewPasswordMail(newPassword, currentUser.getEmail());
            userService.updatePassword(newPassword, currentUser.getLogin());
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return new CommandResult(CommandResult.ResponseType.REDIRECT, request.getHeader("Referer"));
    }

    public CommandResult forgotPassword(HttpServletRequest request) throws CommandException {
        try {
            String newPassword = RandomStringUtils.randomAscii(12);
            UserService userService = new UserServiceImpl();
            User user = userService.getByLogin(request.getParameter(Attribute.USERNAME));
            if (user.getStatus().equals(User.UserStatus.UNCONFIRMED)) {
                throw new CommandException("Users' e-mail is unconfirmed.");
            }
            new EmailSenderServiceImpl().sendNewPasswordMail(newPassword, user.getEmail());
            userService.updatePassword(newPassword, user.getLogin());
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return new CommandResult(CommandResult.ResponseType.REDIRECT, request.getHeader("Referer"));
    }
}
