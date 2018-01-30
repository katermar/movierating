package com.katermar.movierating.command.logic;

import com.katermar.movierating.command.CommandResult;
import com.katermar.movierating.config.Attribute;
import com.katermar.movierating.config.PagePath;
import com.katermar.movierating.config.Parameter;
import com.katermar.movierating.entity.Director;
import com.katermar.movierating.entity.Film;
import com.katermar.movierating.entity.Genre;
import com.katermar.movierating.entity.User;
import com.katermar.movierating.exception.CommandException;
import com.katermar.movierating.exception.ServiceException;
import com.katermar.movierating.service.RegisterService;
import com.katermar.movierating.service.UserService;
import com.katermar.movierating.service.impl.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.katermar.movierating.command.CommandResult.ResponseType.*;

/**
 * Created by katermar on 1/4/2018.
 */
public class GeneralLogic {
    private static final Logger LOGGER = LogManager.getLogger(GeneralLogic.class);

    public CommandResult goToMainPage(HttpServletRequest request) {
        GenreService genreService = new GenreService();
        DirectorServiceImpl directorService = new DirectorServiceImpl();
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

    public CommandResult register(HttpServletRequest request) throws CommandException {
        User user = new User();
        user.setLogin(request.getParameter(Attribute.USERNAME));
        user.setPassword(request.getParameter(Attribute.PASSWORD));
        user.setEmail(request.getParameter(Attribute.EMAIL));
        user.setRealName(request.getParameter(Attribute.REALNAME));
        user.setDateOfBirth(Date.valueOf(LocalDate.parse(
                request.getParameter(Attribute.BIRTHDAY), DateTimeFormatter.ISO_DATE)));

        UserService userService = new UserServiceImpl();
        RegisterService registerService = new RegisterServiceImpl();
        try {
            if (userService.getByLogin(user.getLogin()) == null) {
                registerService.register(user);
                HttpSession session = request.getSession();
                session.setAttribute(Attribute.USER, user);
            } else {
                LOGGER.error("Error occurred while adding user.");
                throw new CommandException("Error occurred while adding user.");
            }
        } catch (ServiceException e) {
            LOGGER.warn(e.getMessage());
        }
        return new CommandResult(REDIRECT, request.getHeader("Referer"));
    }

    public CommandResult switchLanguage(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        String currentLocale = (String) session.getAttribute(Attribute.LOCALE);
        String redirectPage = request.getHeader("Referer");
        if (redirectPage == null) {
            redirectPage = PagePath.MAIN;
        }

        if (Parameter.RUSSIAN_LOCALE.equals(currentLocale)) {
            session.setAttribute(Attribute.LOCALE, Parameter.ENGLISH_LOCALE);
        } else {
            session.setAttribute(Attribute.LOCALE, Parameter.RUSSIAN_LOCALE);
        }
        return new CommandResult(REDIRECT, redirectPage);
    }

    public CommandResult showFilmsPage(HttpServletRequest request) throws CommandException {
        FilmService filmService = new FilmService();
        GenreService genreService = new GenreService();
        DirectorServiceImpl directorService = new DirectorServiceImpl();
        try {
            String pageNumber = Optional.ofNullable(request.getParameter("page")).orElse("1");
            String filmsPerPage = Optional.ofNullable(request.getParameter("filmsPerPage")).orElse("4");
            request.setAttribute("genresModal", genreService.getAll());
            request.setAttribute("directorsModal", directorService.getAll());
            request.setAttribute("films", filmService.getAllFilms(pageNumber, filmsPerPage));
            request.setAttribute("filmsCount", filmService.getFilmsAmount());
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return new CommandResult(FORWARD, PagePath.FILMS);
    }

    public CommandResult showFilmInfoPage(HttpServletRequest request) {
        FilmService filmService = new FilmService();
        RatingService ratingService = new RatingService();
        ReviewService reviewService = new ReviewService();
        GenreService genreService = new GenreService();
        int filmId = Integer.parseInt(request.getParameter("id"));
        try {
            Film film = filmService.getFilmById(filmId);
            request.setAttribute("film", film);
            request.setAttribute("genre", genreService.getByFilm(film.getIdFilm()));
            request.setAttribute("review", reviewService.getByFilm(film.getIdFilm()));
            request.setAttribute("avgRate", ratingService.getAverageRatingByFilm(film.getIdFilm()));
        } catch (ServiceException e) {
            LOGGER.warn(e.getMessage());
        }
        return new CommandResult(FORWARD, PagePath.FILM_INFO);
    }

    public CommandResult goToErrorPage(HttpServletRequest request) {
        return new CommandResult(ERROR, PagePath.ERROR);
    }

    public CommandResult searchFilms(HttpServletRequest request) throws CommandException {
        List<Film> foundFilms;
        Map<String, String[]> parametersMap = new HashMap<>();
        parametersMap.putAll(request.getParameterMap());
        String pageNumber = Optional.ofNullable(request.getParameter("page")).orElse("1");
        String filmsPerPage = Optional.ofNullable(request.getParameter("filmsPerPage")).orElse("4");
        try {
            FilmService filmService = new FilmService();
            foundFilms = filmService.searchFilms(parametersMap, pageNumber, filmsPerPage);
            request.setAttribute("filmsCount", filmService.getSearchFilmsAmount(parametersMap));
            request.setAttribute("genresModal", new GenreService().getAll());
            request.setAttribute("directorsModal", new DirectorServiceImpl().getAll());
        } catch (ServiceException e) {
            LOGGER.warn(e.getMessage());
            throw new CommandException(e);
        }
        List<String> searchDirectors = request.getParameterValues("director") == null ?
                new ArrayList<>() : List.of(request.getParameterValues("director"));
        List<String> searchGenres = request.getParameterValues("genre") == null ?
                new ArrayList<>() : List.of(request.getParameterValues("genre"));

        StringBuilder newUri = new StringBuilder("/controller?");
        request.getParameterMap().entrySet().forEach(stringEntry -> {
            for (String value : stringEntry.getValue()) {
                newUri.append(stringEntry.getKey()).append("=").append(value).append("&");
            }
        });
        request.setAttribute("commandPart", newUri.toString());
        request.setAttribute("films", foundFilms);
        request.setAttribute("genres", searchGenres);
        request.setAttribute("directors", searchDirectors);
        return new CommandResult(REDIRECT, PagePath.REDIRECT_FILMS);
    }

    public CommandResult goToRatingPage(HttpServletRequest request) throws CommandException {
        FilmService filmService = new FilmService();
        try {
            request.setAttribute("filmsMap", filmService.getFilmRatingMapInDescOrder());
        } catch (ServiceException e) {
            LOGGER.warn(e.getMessage());
            throw new CommandException(e);
        }
        return new CommandResult(FORWARD, PagePath.RATING);
    }

    public CommandResult checkLogin(HttpServletRequest request) throws CommandException {
        try {
            if (new UserServiceImpl().getByLogin(request.getParameter(Attribute.USERNAME)).getLogin() != null) {
                request.setAttribute("loginError", "username is in use");
            } else {
                request.setAttribute("loginSuccess", "username isn't used");
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return new CommandResult(FORWARD, PagePath.MAIN);
    }
}
