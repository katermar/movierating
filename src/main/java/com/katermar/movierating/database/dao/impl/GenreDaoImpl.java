package com.katermar.movierating.database.dao.impl;

import com.katermar.movierating.database.connection.ConnectionPool;
import com.katermar.movierating.database.dao.GenreDao;
import com.katermar.movierating.entity.Genre;
import com.katermar.movierating.exception.DAOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by katermar on 1/14/2018.
 * <p>
 * Implementation of the interface, which is used to work with 'genre' table.
 */
public class GenreDaoImpl implements GenreDao {
    private static final String SELECT_GENRE_BY_FILM_ID = "SELECT * FROM genre WHERE name IN (SELECT genrename FROM " +
            "film_genre WHERE idfilm = ?)";
    private static final String SELECT_ALL = "SELECT * FROM genre";
    private static final String DELETE_FROM_FILM_GENRE_WHERE_IDFILM = "DELETE FROM film_genre WHERE idfilm = ?";
    private static final String SELECT_FROM_GENRE_WHERE_NAME = "SELECT * FROM genre WHERE name = ?";
    private static final String INSERT_INTO_GENRE_NAME_VALUES = "INSERT INTO genre (name) VALUES (?)";
    private static final String INSERT_INTO_FILM_GENRE_VALUES_IDFILM_GENRENAME = "INSERT INTO film_genre (idfilm, genrename) VALUES (?, ?)";

    @Override
    public List<Genre> getGenresByFilmId(long id) throws DAOException {
        return getByParameter(SELECT_GENRE_BY_FILM_ID, String.valueOf(id));
    }

    @Override
    public boolean create(Genre genre) throws DAOException {
        try (Connection connectionFromPool = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connectionFromPool.prepareStatement(INSERT_INTO_GENRE_NAME_VALUES)) {
            statement.setString(1, genre.getName());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
        return true;
    }

    @Override
    public Genre constructFromResultSet(ResultSet selected) throws SQLException {
        return new Genre(selected.getString("name"));
    }

    @Override
    public List<Genre> getAll() throws DAOException {
        return getAll(SELECT_ALL);
    }

    @Override
    public Genre getByName(String genreName) throws DAOException {
        return getByParameter(SELECT_FROM_GENRE_WHERE_NAME, genreName).get(0);
    }

    @Override
    public void addGenresForFilm(List<String> genres, int filmId) throws DAOException {
        try (Connection connectionFromPool = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connectionFromPool.prepareStatement(INSERT_INTO_FILM_GENRE_VALUES_IDFILM_GENRENAME)) {
            for (String genre : genres) {
                statement.setInt(1, filmId);
                statement.setString(2, genre);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }

    @Override
    public void deleteByIdFilm(String idFilm) throws DAOException {
        try (Connection connectionFromPool = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connectionFromPool.prepareStatement(DELETE_FROM_FILM_GENRE_WHERE_IDFILM)) {
            statement.setInt(1, Integer.parseInt(idFilm));
            statement.execute();
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }
}
