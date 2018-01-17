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
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public CommandResult register(HttpServletRequest request) {
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

    public CommandResult searchFilms(HttpServletRequest request) {
        GenreService genreService = new GenreService();
        FilmService filmService = new FilmService();
        int minYear = Integer.parseInt(request.getParameter("min-year").isEmpty() ? "0" : request.getParameter("min-year"));
        int maxYear = Integer.parseInt(request.getParameter("max-year").isEmpty() ? "2018" : request.getParameter("max-year"));
        int minDuration = Integer.parseInt(request.getParameter("min-duration").isEmpty() ? "0" : request.getParameter("min-duration"));
        int maxDuration = Integer.parseInt(request.getParameter("max-duration").isEmpty() ? "500" : request.getParameter("max-duration"));
        List<Film> films = null;
        List<Film> foundFilms = new ArrayList<>();
        List<String> searchDirectors = request.getParameterValues("director") == null ?
                new ArrayList<>() : List.of(request.getParameterValues("director"));
        List<String> searchGenres = request.getParameterValues("genre") == null ?
                new ArrayList<>() : List.of(request.getParameterValues("genre"));
        try {
            films = filmService.getAllFilms()
                    .stream()
                    .filter(film ->
                            film.getReleaseYear() <= maxYear && film.getReleaseYear() >= minYear
                                    && film.getDuration() <= maxDuration && film.getDuration() >= minDuration
                                    && searchDirectors.isEmpty() || searchDirectors.contains(film.getDirector().getName()))
                    .collect(Collectors.toList());
            for (Film film : films) {
                if (genreService.getByFilm(film.getIdFilm())
                        .stream()
                        .map(genre -> genre.getName())
                        .collect(Collectors.toList())
                        .containsAll(searchGenres)) {
                    foundFilms.add(film);
                }
            }

        } catch (ServiceException e) {
            LOGGER.warn(e.getMessage());
        }
        request.setAttribute("films", foundFilms);
        request.setAttribute("genres", searchGenres);
        request.setAttribute("directors", searchDirectors);
        return new CommandResult(FORWARD, PagePath.FILMS);
    }

    public CommandResult goToRatingPage(HttpServletRequest request) throws CommandException {
        FilmService filmService = new FilmService();
        try {
//            LOGGER.warn(filmService.getFilmRatingMapInDescOrder());
            request.setAttribute("filmsMap", filmService.getFilmRatingMapInDescOrder());
        } catch (ServiceException e) {
            LOGGER.warn(e.getMessage());
            throw new CommandException(e);
        }
        return new CommandResult(FORWARD, PagePath.RATING);
    }
}
