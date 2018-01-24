package com.katermar.movierating.database.dao.impl;

import com.katermar.movierating.database.connection.ConnectionPool;
import com.katermar.movierating.database.dao.GenericDao;
import com.katermar.movierating.entity.Genre;
import com.katermar.movierating.exception.DAOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
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
    private static final String INSERT_INTO_GENRE_NAME_VALUES = "INSERT INTO genre (name) VALUES (?)";
    private static final String INSERT_INTO_FILM_GENRE_VALUES_IDFILM_GENRENAME = "INSERT INTO film_genre (idfilm, genrename) VALUES (?, ?)";

    public List<Genre> getGenresByFilmId(long id) throws DAOException {
        return getByParameter(SELECT_GENRE_BY_FILM_ID, String.valueOf(id));
    }

    @Override
    public void deleteById(String id) {
    }

    @Override
    public boolean create(Genre genre) throws DAOException {
        try (Connection connectionFromPool = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connectionFromPool.prepareStatement(INSERT_INTO_GENRE_NAME_VALUES)) {
            statement.setString(1, genre.getName());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return true;
    }

    @Override
    public Genre constructFromResultSet(ResultSet selected) throws SQLException {
        return new Genre(selected.getString("name"));
    }

    public List<Genre> getAll() throws DAOException {
        return getAll(SELECT_ALL);
    }

    public Genre getByName(String genreName) throws DAOException {
        return getByParameter(SELECT_FROM_GENRE_WHERE_NAME, genreName).get(0);
    }

    public void addGenresForFilm(List<String> genres, int filmId) throws DAOException {
        try (Connection connectionFromPool = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connectionFromPool.prepareStatement(INSERT_INTO_FILM_GENRE_VALUES_IDFILM_GENRENAME)) {
            for (String genre : genres) {
                statement.setInt(1, filmId);
                statement.setString(2, genre);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }
}
