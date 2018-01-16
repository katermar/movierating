package com.katermar.movierating.database.dao.impl;

import com.katermar.movierating.database.dao.GenericDao;
import com.katermar.movierating.entity.Genre;
import com.katermar.movierating.exception.DAOException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by katermar on 1/14/2018.
 */
public class GenreDaoImpl implements GenericDao<Genre> {
    private static final String SELECT_GENRE_BY_FILM_ID = "SELECT * FROM genre WHERE name IN (SELECT genrename FROM film_genre WHERE idfilm = ?)";
    private static final String SELECT_ALL = "SELECT * FROM genre";
    private static final String SELECT_FROM_GENRE_WHERE_NAME = "SELECT * FROM genre WHERE name = ?";

    public List<Genre> getGenresByFilmId(long id) throws DAOException {
        return getByParameter(SELECT_GENRE_BY_FILM_ID, String.valueOf(id));
    }

    @Override
    public Genre deleteById(String id) {
        return null;
    }

    @Override
    public boolean create(Genre user) throws DAOException {
        return false; //todo
    }

    @Override
    public Genre constructFromResultSet(ResultSet selected) throws SQLException {
        Genre genre = new Genre();
        genre.setName(selected.getString("name"));
        return genre;
    }

    public List<Genre> getAll() throws DAOException {
        return getAll(SELECT_ALL);
    }

    public Genre getByName(String genreName) throws DAOException {
        return getByParameter(SELECT_FROM_GENRE_WHERE_NAME, genreName).get(0);
    }
}
