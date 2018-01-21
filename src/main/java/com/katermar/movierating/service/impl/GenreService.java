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

    public void addGenresForFilm(List<String> genres, int filmId) throws ServiceException {
        try {
            genreDao.addGenresForFilm(genres, filmId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    public List<Genre> getByFilm(long id) throws ServiceException {
        try {
            return genreDao.getGenresByFilmId(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    public List<Genre> getAll() throws ServiceException {
        try {
            return genreDao.getAll();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    public Genre getByName(String genreName) throws ServiceException {
        try {
            return genreDao.getByName(genreName);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    public void addGenre(Genre genre) throws ServiceException {
        try {
            genreDao.create(genre);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
