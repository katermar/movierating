package com.katermar.movierating.command.logic;

import com.katermar.movierating.command.CommandResult;
import com.katermar.movierating.config.PagePath;
import com.katermar.movierating.config.Parameter;
import com.katermar.movierating.entity.Director;
import com.katermar.movierating.entity.Film;
import com.katermar.movierating.entity.Genre;
import com.katermar.movierating.entity.User;
import com.katermar.movierating.exception.BadRequestException;
import com.katermar.movierating.exception.CommandException;
import com.katermar.movierating.exception.ServiceException;
import com.katermar.movierating.service.UserService;
import com.katermar.movierating.service.impl.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.katermar.movierating.command.CommandResult.ResponseType.*;

/**
 * Created by katermar on 1/9/2018.
 */
public class AdminLogic {
    private static final Logger LOGGER = LogManager.getLogger(AdminLogic.class);

    public CommandResult banUser(HttpServletRequest request) throws CommandException, BadRequestException {
        HttpSession session = request.getSession(false);
        if (session == null) {
            throw new CommandException("Session isn't opened.");
        }
        User currentUser = (User) session.getAttribute(Parameter.USER);
        String loginToBan = request.getParameter("login");
        AdminServiceImpl adminService = new AdminServiceImpl();
        if (currentUser.getRole() == User.UserRole.ADMIN) {
            try {
                if (new UserServiceImpl().getByLogin(loginToBan).getRole() == User.UserRole.ADMIN) {
                    return new CommandResult(ERROR, 400, "You are not allowed to ban Admin");
                }
                adminService.updateBan(loginToBan);
            } catch (ServiceException e) {
                throw new CommandException(e.getMessage());
            }
        }
        return new CommandResult(REDIRECT, request.getHeader("Referer"));
    }

    public CommandResult showUsersPage(HttpServletRequest request) throws CommandException {
        UserService userService = new UserServiceImpl();
        try {
            request.setAttribute("users", userService.getUserRatingMap());
        } catch (ServiceException e) {
            LOGGER.warn(e.getMessage());
            throw new CommandException(e.getMessage());
        }
        return new CommandResult(FORWARD, PagePath.USERS);
    }

    public CommandResult showAddPage(HttpServletRequest request) {
        GenreServiceImpl genreService = new GenreServiceImpl();
        DirectorServiceImpl directorService = new DirectorServiceImpl();
        try {
            List<Genre> genres = genreService.getAll();
            request.setAttribute("genres", genres);
            List<Director> directors = directorService.getAll();
            request.setAttribute("directors", directors);
        } catch (ServiceException e) {
            LOGGER.warn(e.getMessage());
        }
        return new CommandResult(FORWARD, PagePath.ADD);
    }

    public CommandResult addFilm(HttpServletRequest request) throws CommandException {
        try {
            List<String> genres = request.getParameterValues(Parameter.GENRE) == null ?
                    new ArrayList<>() : List.of(request.getParameterValues(Parameter.GENRE));
            Film film = new Film();
            film.setName(request.getParameter(Parameter.NAME));
            film.setDuration(Double.parseDouble(request.getParameter(Parameter.DURATION)));
            film.setReleaseYear(Date.valueOf(LocalDate.now().withYear(Integer.parseInt(request.getParameter(Parameter.YEAR)))));
            DirectorServiceImpl directorService = new DirectorServiceImpl();
            film.setDirector(directorService.getByName(request.getParameter(Parameter.DIRECTOR)));
            film.setIdDirector(film.getDirector().getId());
            film.setDescription(request.getParameter(Parameter.DESCRIPTION));
            film.setPoster(request.getParameter(Parameter.POSTER));
            FilmServiceImpl filmService = new FilmServiceImpl();
            GenreServiceImpl genreService = new GenreServiceImpl();

            if (request.getParameter("mode") != null) {
                genreService.deleteByIdFilm(request.getParameter("id"));
            }

            filmService.addFilm(film);
            genreService.addGenresForFilm(genres, filmService.getFilmByName(film.getName()).getId());
        } catch (ServiceException e) {
            LOGGER.warn(e.getMessage());
            throw new CommandException(e.getMessage());
        }
        return new CommandResult(REDIRECT, request.getHeader("Referer"));
    }

    public CommandResult addGenre(HttpServletRequest request) throws CommandException, BadRequestException {
        try {
            GenreServiceImpl genreService = new GenreServiceImpl();
            String name = request.getParameter("name");
            if (name == null || name.isEmpty()) {
                throw new BadRequestException();
            }
            genreService.addGenre(new Genre(name));
        } catch (ServiceException e) {
            LOGGER.warn(e.getMessage());
            throw new CommandException(e.getMessage());
        }
        return new CommandResult(REDIRECT, request.getHeader("Referer"));
    }

    public CommandResult addDirector(HttpServletRequest request) throws CommandException, BadRequestException {
        DirectorServiceImpl directorService = new DirectorServiceImpl();
        try {
            String name = request.getParameter("name");
            if (name == null || name.isEmpty()) {
                throw new BadRequestException();
            }
            directorService.addDirector(new Director(name));
        } catch (ServiceException e) {
            LOGGER.warn(e.getMessage());
            throw new CommandException(e.getMessage());
        }
        return new CommandResult(REDIRECT, request.getHeader("Referer"));
    }

    public CommandResult deleteFilm(HttpServletRequest request) throws CommandException {
        FilmServiceImpl filmService = new FilmServiceImpl();
        try {
            filmService.deleteById(request.getParameter("id"));
        } catch (ServiceException e) {
            LOGGER.error(e.getMessage());
            throw new CommandException(e.getMessage());
        }
        return new CommandResult(REDIRECT, request.getHeader("Referer"));
    }
}
