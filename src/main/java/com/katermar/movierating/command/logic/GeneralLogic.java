package com.katermar.movierating.command.logic;

import com.katermar.movierating.command.CommandResult;
import com.katermar.movierating.config.PagePath;
import com.katermar.movierating.config.Parameter;
import com.katermar.movierating.config.Property;
import com.katermar.movierating.entity.Director;
import com.katermar.movierating.entity.Film;
import com.katermar.movierating.entity.Genre;
import com.katermar.movierating.entity.User;
import com.katermar.movierating.exception.CommandException;
import com.katermar.movierating.exception.ServiceException;
import com.katermar.movierating.filter.ValidationFilter;
import com.katermar.movierating.service.*;
import com.katermar.movierating.service.impl.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.katermar.movierating.command.CommandResult.ResponseType.FORWARD;
import static com.katermar.movierating.command.CommandResult.ResponseType.REDIRECT;

/**
 * Created by katermar on 1/4/2018.
 * <p>
 * The class with a bunch of General commands.
 * These commands can be executed even if session isn't opened.
 */
public class GeneralLogic {
    private static final Logger LOGGER = LogManager.getLogger(GeneralLogic.class);
    private UserService userService = new UserServiceImpl();
    private FilmService filmService = new FilmServiceImpl();
    private RatingService ratingService = new RatingServiceImpl();
    private ReviewService reviewService = new ReviewServiceImpl();
    private GenreService genreService = new GenreServiceImpl();
    private DirectorService directorService = new DirectorServiceImpl();


    /**
     * Method to show main page
     *
     * @param request
     * @return an instance of the CommandResult to forward on the main page
     */
    public CommandResult showMainPage(HttpServletRequest request) {
        try {
            List<Genre> genres = genreService.getAll();
            request.setAttribute("genres", genres);
            List<Director> directors = directorService.getAll();
            request.setAttribute("directors", directors);
        } catch (ServiceException e) {
            LOGGER.warn(e.getMessage());
        }
        return new CommandResult(FORWARD, PagePath.MAIN);
    }

    /**
     * Method to register new user from the values of the request parameters
     * Values are considered to be valid after validation in filter
     *
     * @param request
     * @return an instance of the CommandResult class to redirect on the referrer got from header
     * @throws CommandException
     * @see ValidationFilter
     */
    public CommandResult register(HttpServletRequest request) throws CommandException {
        String login = request.getParameter(Parameter.USERNAME).trim();
        String email = request.getParameter(Parameter.EMAIL).trim();
        String password = request.getParameter(Parameter.PASSWORD).trim();

        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        user.setEmail(email);
        user.setRealName(request.getParameter(Parameter.REALNAME));
        user.setDateOfBirth(Date.valueOf(LocalDate.parse(
                request.getParameter(Parameter.BIRTHDAY), DateTimeFormatter.ISO_DATE)));

        RegisterService registerService = new RegisterServiceImpl();
        try {
            if (userService.getByLogin(user.getLogin()).getLogin() == null) {
                registerService.register(user);
//                HttpSession session = request.getSession();
//                session.setAttribute(Parameter.USER, userService.getByLogin(login));
            } else {
                LOGGER.error("Error occurred while adding user.");
                throw new CommandException("Error occurred while adding user.");
            }
        } catch (ServiceException e) {
            LOGGER.warn(e.getMessage());
        }
        return new CommandResult(REDIRECT, request.getHeader("Referer"));
    }

    /**
     * Method to switch language. Gives ability to choose from russian and english
     * Selects the opposite to the current language
     *
     * @param request
     * @return an instance of the CommandResult class
     * to redirect on the referrer got from header or main page if it isn't present
     */
    public CommandResult switchLanguage(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        String currentLocale = (String) session.getAttribute(Parameter.LOCALE);
        String redirectPage = request.getHeader("Referer");
        if (redirectPage == null) {
            redirectPage = PagePath.REDIRECT_MAIN;
        }

        if (Property.RUSSIAN_LOCALE.equals(currentLocale)) {
            session.setAttribute(Parameter.LOCALE, Property.ENGLISH_LOCALE);
        } else {
            session.setAttribute(Parameter.LOCALE, Property.RUSSIAN_LOCALE);
        }
        return new CommandResult(REDIRECT, redirectPage);
    }

    /**
     * Method that shows films page.
     * Sets page number to the first page and films per page as 4 items on the page as a default
     *
     * @param request
     * @return an instance of the CommandResult to forward on the films page
     * @throws CommandException
     */
    public CommandResult showFilmsPage(HttpServletRequest request) throws CommandException {
        try {
            String pageNumber = Optional.ofNullable(request.getParameter("page")).orElse("1");
            String filmsPerPage = Optional.ofNullable(request.getParameter("filmsPerPage")).orElse("4");
            request.setAttribute("genresModal", genreService.getAll());
            request.setAttribute("directorsModal", directorService.getAll());
            request.setAttribute("films", filmService.getAllFilms(pageNumber, filmsPerPage));
            request.setAttribute("filmsCount", filmService.getFilmsAmount());
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage());
        }
        return new CommandResult(FORWARD, PagePath.FILMS);
    }

    /**
     * Method to show film info page
     *
     * @param request
     * @return an instance of the CommandResult to forward on the films info page
     */
    public CommandResult showFilmInfoPage(HttpServletRequest request) {
        int filmId = Integer.parseInt(request.getParameter("id"));
        try {
            Film film = filmService.getFilmById(filmId);
            request.setAttribute("film", film);
            request.setAttribute("genre", genreService.getByFilm(film.getId()));
            request.setAttribute("review", reviewService.getByFilm(film.getId()));
            request.setAttribute("avgRate", ratingService.getAverageRatingByFilm(film.getId()));
        } catch (ServiceException e) {
            LOGGER.warn(e.getMessage());
        }
        return new CommandResult(FORWARD, PagePath.FILM_INFO);
    }

    /**
     * Method which is used to search films by parameters map taken from the request
     * Sets page number to the first page and films per page as 4 items on the page as a default
     * Also sets command as a command part attribute to use pagination properly
     *
     * @param request
     * @return an instance of the CommandResult to forward on the films info page
     * @throws CommandException
     */
    public CommandResult searchFilms(HttpServletRequest request) throws CommandException {
        List<Film> foundFilms;
        Map<String, String[]> requestParameters = new HashMap<>(request.getParameterMap());
        String pageNumber = Optional.ofNullable(request.getParameter("page")).orElse("1");
        String filmsPerPage = Optional.ofNullable(request.getParameter("filmsPerPage")).orElse("4");

        try {
            foundFilms = filmService.searchFilms(requestParameters, pageNumber, filmsPerPage);
            request.setAttribute("filmsCount", filmService.getSearchFilmsAmount(requestParameters));
            request.setAttribute("genresModal", new GenreServiceImpl().getAll());
            request.setAttribute("directorsModal", new DirectorServiceImpl().getAll());
        } catch (ServiceException e) {
            LOGGER.warn(e.getMessage());
            throw new CommandException(e.getMessage());
        }
        List<String> searchDirectors = request.getParameterValues("director") == null ?
                new ArrayList<>() : List.of(request.getParameterValues("director"));
        List<String> searchGenres = request.getParameterValues("genre") == null ?
                new ArrayList<>() : List.of(request.getParameterValues("genre"));

        StringBuilder newUri = new StringBuilder("/controller?");
        request.getParameterMap().forEach((key, value1) -> {
            for (String value : value1) {
                newUri.append(key).append("=").append(value).append("&");
            }
        });
        request.setAttribute("commandPart", newUri.toString());
        request.setAttribute("films", foundFilms);
        request.setAttribute("genres", searchGenres);
        request.setAttribute("directors", searchDirectors);
        return new CommandResult(FORWARD, PagePath.FILMS);
    }

    /**
     * Method to show films rating page
     *
     * @param request
     * @return an instance of the CommandResult to forward on the films rating page
     * @throws CommandException
     */
    public CommandResult showRatingPage(HttpServletRequest request) throws CommandException {
        try {
            request.setAttribute("filmsMap", filmService.getFilmRatingMapInDescOrder());
        } catch (ServiceException e) {
            LOGGER.warn(e.getMessage());
            throw new CommandException(e.getMessage());
        }
        return new CommandResult(FORWARD, PagePath.RATING);
    }

    /**
     * Method which is used only in the login form to check if login
     * is already taken and to represent it in the register form as ajax success result
     *
     * @param request
     * @return an instance of the CommandResult to forward on the main page
     * @throws CommandException
     */
    public CommandResult checkLogin(HttpServletRequest request) throws CommandException {
        try {
            if (new UserServiceImpl().getByLogin(request.getParameter(Parameter.USERNAME)).getLogin() != null) {
                request.setAttribute("loginError", "username is in use");
            } else {
                request.setAttribute("loginSuccess", "username isn't used");
            }
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage());
        }
        return new CommandResult(FORWARD, PagePath.MAIN);
    }
}
