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
    private static final GenreService GENRE_SERVICE = new GenreService();


    public Map<Film, Double> getFilmRatingMapInDescOrder() throws ServiceException {
        try {
            Map<Film, Double> filmRatingMap = new LinkedHashMap<>();
            for (Film film : appendDirectorAndGenre(FILM_DAO.getOrderedByRatingDesc())) {
                filmRatingMap.put(film, RATING_SERVICE.getAverageRatingByFilm(film.getIdFilm()));
            }
            return filmRatingMap;
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    public List<Film> getAllFilms(String pageNumber, String filmsPerPage) throws ServiceException {
        try {
            return appendDirectorAndGenre(FILM_DAO.getAll(Integer.parseInt(pageNumber), Integer.valueOf(filmsPerPage)));
        } catch (DAOException e) {
            throw new ServiceException(e);
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
            return appendDirectorAndGenre(FILM_DAO.getFilmsByDirector(directorId));
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    public List<Film> getFilmsByGenre(String genreName) throws ServiceException {
        try {
            return appendDirectorAndGenre(FILM_DAO.getFilmsByGenre(genreName));
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
            Film film = FILM_DAO.getByName(name);
            film.setDirector(DIRECTOR_DAO.getDirectorByFilm(film.getIdFilm()));
            return film;
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    public List<Film> searchFilms(Map<String, String[]> parametersMap, String pageNumber, String filmsPerPage) throws ServiceException {
        try {
            List.of("min-year", "max-year", "min-duration", "max-duration").forEach(param -> {
                if (parametersMap.containsKey(param) && parametersMap.get(param)[0].isEmpty()) {
                    parametersMap.remove(param);
                }
            });
            return appendDirectorAndGenre(FILM_DAO
                    .searchFilms(parametersMap, Integer.parseInt(pageNumber), Integer.parseInt(filmsPerPage)));
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    public int getSearchFilmsAmount(Map<String, String[]> parametersMap) throws ServiceException {
        try {
            return FILM_DAO.searchFilmsAmount(parametersMap);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    public void deleteById(String id) throws ServiceException {
        try {
            FILM_DAO.deleteById(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    private List<Film> appendDirectorAndGenre(List<Film> films) throws DAOException, ServiceException {
        for (Film film : films) {
            film.setDirector(DIRECTOR_DAO.getDirectorByFilm(film.getIdFilm()));
            film.setGenres(GENRE_SERVICE.getByFilm(film.getIdFilm()));
        }
        return films;
    }
}
