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

    public List<Genre> findGenresByFilmId(long id) throws DAOException {
        return findByParameter(String.valueOf(id), SELECT_GENRE_BY_FILM_ID);
    }

    @Override
    public Genre deleteById(long id) {
        return null;
    }

    @Override
    public Genre constructFromResultSet(ResultSet selected) throws SQLException {
        Genre genre = new Genre();
        genre.setName(selected.getString("name"));
        return genre;
    }
}
