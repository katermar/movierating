package com.katermar.movierating.service.impl;

import com.katermar.movierating.database.dao.impl.GenreDaoImpl;
import com.katermar.movierating.entity.Genre;
import com.katermar.movierating.exception.DAOException;
import com.katermar.movierating.exception.ServiceException;

import java.util.List;

/**
 * Created by katermar on 1/14/2018.
 */
public class GenreService {
    private static final GenreDaoImpl genreDao = new GenreDaoImpl();

    public List<Genre> getByFilm(long id) throws ServiceException {
        try {
            return genreDao.getGenresByFilmId(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
