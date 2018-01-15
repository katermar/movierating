package com.katermar.movierating.service.impl;

import com.katermar.movierating.database.dao.impl.DirectorDaoImpl;
import com.katermar.movierating.database.dao.impl.FilmDaoImpl;
import com.katermar.movierating.entity.Film;
import com.katermar.movierating.exception.DAOException;
import com.katermar.movierating.exception.ServiceException;

import java.util.List;

/**
 * Created by katermar on 1/12/2018.
 */
public class FilmService {
    private static final FilmDaoImpl FILM_DAO = new FilmDaoImpl();
    private static final DirectorDaoImpl DIRECTOR_DAO = new DirectorDaoImpl();

    public List<Film> getAllFilms() throws ServiceException {
        try {
            return FILM_DAO.getAll();
        } catch (DAOException e) {
            throw new ServiceException(e); // todo
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
}
