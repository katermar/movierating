package com.katermar.movierating.service.impl;

import com.katermar.movierating.database.dao.impl.GenreDaoImpl;
import com.katermar.movierating.entity.Genre;
import com.katermar.movierating.exception.DAOException;
import com.katermar.movierating.exception.ServiceException;

import java.util.List;

/**
 * Created by katermar on 1/14/2018.
 */
public class GenreServiceImpl implements com.katermar.movierating.service.GenreService {
    private static final GenreDaoImpl GENRE_DAO = new GenreDaoImpl();

    @Override
    public void addGenresForFilm(List<String> genres, int filmId) throws ServiceException {
        try {
            GENRE_DAO.addGenresForFilm(genres, filmId);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public List<Genre> getByFilm(long id) throws ServiceException {
        try {
            return GENRE_DAO.getGenresByFilmId(id);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public List<Genre> getAll() throws ServiceException {
        try {
            return GENRE_DAO.getAll();
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public Genre getByName(String genreName) throws ServiceException {
        try {
            return GENRE_DAO.getByName(genreName);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public void addGenre(Genre genre) throws ServiceException {
        try {
            GENRE_DAO.create(genre);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public void deleteByIdFilm(String idFilm) throws ServiceException {
        try {
            GENRE_DAO.deleteByIdFilm(idFilm);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }
}
