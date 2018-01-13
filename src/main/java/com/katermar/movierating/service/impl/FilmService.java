package com.katermar.movierating.service.impl;

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

    public List<Film> getAllFilms() throws ServiceException {
        try {
            return FILM_DAO.findAll();
        } catch (DAOException e) {
            throw new ServiceException(e); // todo
        }
    }

    public Film findFilmById(long id) throws ServiceException {
        try {
            return FILM_DAO.findById(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
