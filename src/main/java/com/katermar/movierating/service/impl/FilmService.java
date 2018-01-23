package com.katermar.movierating.service.impl;

import com.katermar.movierating.database.dao.impl.DirectorDaoImpl;
import com.katermar.movierating.database.dao.impl.FilmDaoImpl;
import com.katermar.movierating.entity.Film;
import com.katermar.movierating.exception.DAOException;
import com.katermar.movierating.exception.ServiceException;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by katermar on 1/12/2018.
 */
public class FilmService {
    private static final FilmDaoImpl FILM_DAO = new FilmDaoImpl();
    private static final DirectorDaoImpl DIRECTOR_DAO = new DirectorDaoImpl();
    private static final RatingService RATING_SERVICE = new RatingService();


    public Map<Film, Double> getFilmRatingMapInDescOrder() throws ServiceException {
        try {
            Map<Film, Double> filmRatingMap = new LinkedHashMap<>();
            for (Film film : FILM_DAO.getOrderedByRatingDesc()) {
                filmRatingMap.put(film, RATING_SERVICE.getAverageRatingByFilm(film.getIdFilm()));
            }
            return filmRatingMap;
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    public List<Film> getAllFilms(String pageNumber, String filmsPerPage) throws ServiceException {
        try {
            List<Film> films = FILM_DAO.getAll(Integer.parseInt(pageNumber), Integer.valueOf(filmsPerPage));
            for (Film film : films) {
                film.setDirector(DIRECTOR_DAO.getDirectorByFilm(film.getIdFilm()));
            }
            return films;
        } catch (DAOException e) {
            throw new ServiceException(e); // todo
        }
    }

    public int getFilmsAmount() throws ServiceException {
        try {
            return FILM_DAO.getFilmsAmount();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    public Film getFilmById(long id) throws ServiceException {
        try {
            Film film = FILM_DAO.getById(id);
            film.setDirector(DIRECTOR_DAO.getDirectorByFilm(film.getIdFilm()));
            return film;
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    public List<Film> getFilmsByDirector(int directorId) throws ServiceException {
        try {
            List<Film> films = FILM_DAO.getFilmsByDirector(directorId);
            for (Film film : films) {
                film.setDirector(DIRECTOR_DAO.getDirectorByFilm(film.getIdFilm()));
            }
            return films;
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    public List<Film> getFilmsByGenre(String genreName) throws ServiceException {
        try {
            return FILM_DAO.getFilmsByGenre(genreName);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    public void addFilm(Film film) throws ServiceException {
        try {
            FILM_DAO.create(film);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    public Film getFilmByName(String name) throws ServiceException {
        try {
            return FILM_DAO.getByName(name);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
