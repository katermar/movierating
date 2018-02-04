package com.katermar.movierating.command.logic;

import com.katermar.movierating.command.CommandResult;
import com.katermar.movierating.config.PagePath;
import com.katermar.movierating.config.Parameter;
import com.katermar.movierating.entity.Rating;
import com.katermar.movierating.entity.Review;
import com.katermar.movierating.entity.User;
import com.katermar.movierating.exception.CommandException;
import com.katermar.movierating.exception.ServiceException;
import com.katermar.movierating.service.*;
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

import static com.katermar.movierating.command.CommandResult.ResponseType.FORWARD;

/**
 * Created by katermar on 12/31/2017.
 * <p>
 * The class with a bunch of User commands.
 * These commands can be executed only if session is opened and user is present in this session
 */
public class UserLogic {
    private static final Logger LOGGER = LogManager.getLogger(UserLogic.class);
    private RegisterService registerService = new RegisterServiceImpl();
    private UserService userService = new UserServiceImpl();
    private RatingService ratingService = new RatingServiceImpl();
    private ReviewService reviewService = new ReviewServiceImpl();
    private EmailSenderService emailSenderService = new EmailSenderServiceImpl();

    /**
     * Method to confirm user profile by email, decrypts username taken from the url
     * Puts new user in the current session
     *
     * @param request
     * @return an instance of the CommandResult to forward on the profile page
     * @throws CommandException
     */
    public CommandResult confirmEmail(HttpServletRequest request) throws CommandException {
        String login = request.getParameter(Parameter.USER).replace(" ", "+");
        BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
        textEncryptor.setPassword(ResourceBundle.getBundle("project").getString("encryption.password"));
        try {
            HttpSession session = request.getSession(false);
            if (session != null) {
                User currentUser = (User) session.getAttribute(Parameter.USER);
                if (currentUser.getLogin().equals(textEncryptor.decrypt(login))) {
                    currentUser.setStatus(User.UserStatus.UNBANED);
                    registerService.confirmEmail(userService.getByLogin(textEncryptor.decrypt(login)));
                }
                session.removeAttribute(Parameter.USER);
                session.setAttribute(Parameter.USER, currentUser);
            }
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage());
        }
        return new CommandResult(FORWARD, PagePath.PROFILE);
    }

    /**
     * Method to logout. Removes user from the session attributes and invalidates current session.
     *
     * @param request
     * @return an instance of the CommandResult to redirect on the main page
     */
    public CommandResult logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute(Parameter.USER);
            session.invalidate();
        }
        return new CommandResult(CommandResult.ResponseType.REDIRECT, PagePath.REDIRECT_MAIN);
    }

    /**
     * Method to log in by parameters taken from the request.
     * If user with the login is present opens ne session and puts him as an attribute
     *
     * @param request
     * @return an instance of the CommandResult to forward on the main page
     * @throws CommandException
     */
    public CommandResult login(HttpServletRequest request) throws CommandException {
        AuthSecurityService authSecurityService = new AuthSecurityServiceImpl();

        String login = request.getParameter(Parameter.USERNAME);
        String password = request.getParameter(Parameter.PASSWORD);

        User user = null;
        try {
            user = authSecurityService.checkUserCredentials(login, password);
            if (user == null || user.isBaned()) {
                request.setAttribute(Parameter.LOGIN_ERROR, "User credentials are invalid. Try again!");
            } else {
                HttpSession session = request.getSession();
                session.setAttribute(Parameter.USER, user);
                session.setMaxInactiveInterval(500);
            }
        } catch (ServiceException e) {
            throw new CommandException();
        }
        return new CommandResult(FORWARD, PagePath.MAIN);
    }

    public CommandResult showProfilePage(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession(false);
        if (session == null) {
            throw new CommandException("Session is over.");
        }
        User currentUser = (User) session.getAttribute(Parameter.USER);
        try {
            Map<String, Long> ratingMap = ratingService.getRatingMapByUser(currentUser.getId());
            request.setAttribute("total", ratingMap.values().stream().reduce(0L, Long::sum));
            request.setAttribute("ratings", ratingMap);
            request.setAttribute("userAverageRating", ratingService.getAverageRatingByUser(currentUser.getId()));
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage());
        }

        return new CommandResult(FORWARD, PagePath.PROFILE);
    }

    /**
     * Method to leave a review
     *
     * @param request
     * @return an instance of the CommandResult class to redirect on the referrer got from header
     * @throws CommandException
     */
    public CommandResult leaveReview(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession(false);
        if (session == null) {
            throw new CommandException("Session is over.");
        }
        User currentUser = (User) session.getAttribute(Parameter.USER);

        Review review = new Review();
        review.setUser(currentUser);
        review.setIdUser(currentUser.getId());
        if (request.getParameter("review").trim().isEmpty()) {
            throw new CommandException("Review is empty!");
        }
        review.setText(request.getParameter("review"));
        review.setIdFilm(Integer.parseInt(request.getParameter("id")));
        try {
            reviewService.addReview(review);
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage());
        }
        return new CommandResult(CommandResult.ResponseType.REDIRECT, request.getHeader("Referer"));
    }

    public CommandResult addRating(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession(false);
        if (session == null) {
            throw new CommandException("Session is over.");
        }
        User currentUser = (User) session.getAttribute(Parameter.USER);
        Rating rating = new Rating();
        rating.setIdFilm(Integer.parseInt(request.getParameter("id")));
        rating.setIdUser(currentUser.getId());
        rating.setIsSeen(true);
        rating.setRatingAmount(Integer.parseInt(request.getParameter("rating")));
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
        User currentUser = (User) session.getAttribute(Parameter.USER);
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

    /**
     * Method to set an avatar. User can set every avatar
     * which is less than 300x300 px or in .svg format
     *
     * @param request
     * @return an instance of the CommandResult class to redirect on the referrer got from header
     * @throws CommandException
     */
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
            User currentUser = (User) request.getSession(false).getAttribute("user");
            currentUser.setAvatar(imageURL);
            userService.addUser(currentUser);
        } catch (IOException | ServiceException e) {
            LOGGER.warn(e.getMessage());
            throw new CommandException(e.getMessage());
        }
        return new CommandResult(CommandResult.ResponseType.REDIRECT, request.getHeader("Referer"));
    }

    /**
     * Method to send confirmation mail.
     *
     * @param request
     * @return an instance of the CommandResult class to redirect on the referrer got from header
     * @throws CommandException
     */
    public CommandResult sendEmail(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession(false);
        if (session == null) {
            throw new CommandException("Session is over.");
        }
        User currentUser = (User) session.getAttribute(Parameter.USER);
        currentUser.setEmail(request.getParameter("email"));
        try {
            userService.addUser(currentUser);
            emailSenderService.sendConfirmationMail(currentUser.getLogin(), currentUser.getEmail());
        } catch (ServiceException e) {
            LOGGER.error(e.getMessage());
            throw new CommandException(e.getMessage());
        }
        return new CommandResult(CommandResult.ResponseType.REDIRECT, request.getHeader("Referer"));
    }

    /**
     * Method to edit users' profile with ability to change email, birthday and real name.
     * If email is new it needs to be confirmed.
     *
     * @param request
     * @return an instance of the CommandResult class to redirect on the referrer got from header
     * @throws CommandException
     */
    public CommandResult editProfile(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession(false);
        if (session == null) {
            throw new CommandException("Session is over.");
        }
        User currentUser = (User) session.getAttribute(Parameter.USER);

        try {
            String email = request.getParameter("email");
            if (!email.equals(currentUser.getEmail())) {
                userService.updateStatus(User.UserStatus.UNCONFIRMED, currentUser.getLogin());
            }
            currentUser.setEmail(email);
            String birthday = request.getParameter("birthday");
            currentUser.setDateOfBirth(Date.valueOf(LocalDate.parse(birthday, DateTimeFormatter.ISO_DATE)));
            currentUser.setRealName(request.getParameter("realname"));
            userService.addUser(currentUser);
            session.removeAttribute(Parameter.USER);
            session.setAttribute(Parameter.USER, currentUser);
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage());
        }
        return new CommandResult(CommandResult.ResponseType.REDIRECT, request.getHeader("Referer"));
    }

    /**
     * Method to send new generated password on the users email to change it.
     *
     * @param request
     * @return an instance of the CommandResult class to redirect on the referrer got from header
     * @throws CommandException
     */
    public CommandResult sendNewPassword(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession(false);
        if (session == null) {
            throw new CommandException("Session is over.");
        }
        User currentUser = (User) session.getAttribute(Parameter.USER);
        if (currentUser.getStatus().equals(User.UserStatus.UNCONFIRMED)) {
            throw new CommandException("Users' e-mail is unconfirmed.");
        }
        String newPassword = RandomStringUtils.randomAscii(12);
        try {
            emailSenderService.sendNewPasswordMail(newPassword, currentUser.getEmail());
            userService.updatePassword(newPassword, currentUser.getLogin());
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage());
        }
        return new CommandResult(CommandResult.ResponseType.REDIRECT, request.getHeader("Referer"));
    }

    /**
     * Method to send new password if user has forgotten it
     *
     * @param request
     * @return an instance of the CommandResult class to redirect on the referrer got from header
     * @throws CommandException
     */
    public CommandResult forgotPassword(HttpServletRequest request) throws CommandException {
        try {
            String newPassword = RandomStringUtils.randomAscii(12);
            User user = userService.getByLogin(request.getParameter(Parameter.USERNAME));
            if (user.getStatus().equals(User.UserStatus.UNCONFIRMED)) {
                throw new CommandException("Users' e-mail is unconfirmed.");
            }
            emailSenderService.sendNewPasswordMail(newPassword, user.getEmail());
            userService.updatePassword(newPassword, user.getLogin());
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage());
        }
        return new CommandResult(CommandResult.ResponseType.REDIRECT, request.getHeader("Referer"));
    }
}
