package com.katermar.movierating.command.logic;

import com.katermar.movierating.command.CommandResult;
import com.katermar.movierating.config.Attribute;
import com.katermar.movierating.config.PagePath;
import com.katermar.movierating.config.Parameter;
import com.katermar.movierating.entity.Film;
import com.katermar.movierating.entity.User;
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
import java.util.List;

import static com.katermar.movierating.command.CommandResult.ResponseType.*;

/**
 * Created by katermar on 1/4/2018.
 */
public class GeneralLogic {
    private static final Logger LOGGER = LogManager.getLogger(GeneralLogic.class);

    public CommandResult goToMainPage(HttpServletRequest request) {
        return new CommandResult(FORWARD, PagePath.MAIN);
    }

    public CommandResult register(HttpServletRequest request) {
        User user = new User();
        user.setLogin(request.getParameter(Attribute.USERNAME));
        user.setPassword(request.getParameter(Attribute.PASSWORD));
        user.setEmail(request.getParameter(Attribute.EMAIL));
        user.setRealName(request.getParameter(Attribute.REALNAME));
        user.setDateOfBirth(Date.valueOf(LocalDate.parse(request.getParameter(Attribute.BIRTHDAY), DateTimeFormatter.ISO_DATE)));

        UserService userService = new UserServiceImpl();
        RegisterService registerService = new RegisterServiceImpl();
        try {
            if (userService.getByLogin(user.getLogin()) == null) {
                registerService.register(user);
                HttpSession session = request.getSession();
                session.setAttribute(Attribute.USER, user);
            } else {
                //todo error message page
            }
        } catch (ServiceException e) {
            LOGGER.warn(e.getMessage());
        }
        return new CommandResult(FORWARD, PagePath.MAIN);
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

    public CommandResult showFilmsPage(HttpServletRequest request) {
        FilmService filmService = new FilmService();
        try {
            request.setAttribute("films", filmService.getAllFilms());
        } catch (ServiceException e) {
            LOGGER.warn(e.getMessage()); //todo
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


    public CommandResult showFilmsByDirector(HttpServletRequest request) {
        int directorId = Integer.parseInt(request.getParameter("id"));
        FilmService filmService = new FilmService();
        try {
            List<Film> films = filmService.getFilmsByDirector(directorId);
            request.setAttribute("films", films);
            request.setAttribute("director", films.get(0).getDirector().getName());
        } catch (ServiceException e) {
            LOGGER.warn(e.getMessage());
        }
        return new CommandResult(FORWARD, PagePath.FILMS);
    }

    public CommandResult showFilmsByGenre(HttpServletRequest request) {
        String genreName = request.getParameter("genre");
        FilmService filmService = new FilmService();
        try {
            List<Film> films = filmService.getFilmsByGenre(genreName);
            request.setAttribute("films", films);
            request.setAttribute("genre", genreName);
        } catch (ServiceException e) {
            LOGGER.warn(e.getMessage());
        }
        return new CommandResult(FORWARD, PagePath.FILMS);
    }
}
