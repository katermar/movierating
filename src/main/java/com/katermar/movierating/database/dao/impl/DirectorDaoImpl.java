package com.katermar.movierating.database.dao.impl;

import com.katermar.movierating.database.connection.ConnectionPool;
import com.katermar.movierating.database.dao.GenericDao;
import com.katermar.movierating.entity.Director;
import com.katermar.movierating.exception.DAOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by katermar on 1/15/2018.
 */
public class DirectorDaoImpl implements GenericDao<Director> {
    private static final String SELECT_ALL = "SELECT * FROM director";
    private static final String SELECT_FROM_DIRECTOR_WHERE_NAME = "SELECT * FROM director WHERE name = ?";
    private static final String INSERT_INTO_DIRECTOR_NAME_VALUES = "INSERT INTO director (name) VALUES (?)";
    private static final String SELECT_DIRECTOR_BY_FILM = "SELECT * FROM director WHERE iddirector IN (SELECT iddirector FROM film WHERE idfilm = ?)";

    public Director getDirectorByFilm(int filmId) throws DAOException {
        return getByParameter(SELECT_DIRECTOR_BY_FILM, String.valueOf(filmId)).get(0);
    }

    @Override
    public void deleteById(String id) {
    }

    @Override
    public boolean create(Director director) throws DAOException {
        try (Connection connectionFromPool = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connectionFromPool.prepareStatement(INSERT_INTO_DIRECTOR_NAME_VALUES)) {
            statement.setString(1, director.getName());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return true;
    }

    @Override
    public Director constructFromResultSet(ResultSet selected) throws SQLException {
        Director director = new Director(selected.getString("name"));
        director.setIddirector(selected.getInt("iddirector"));
        return director;
    }

    public List<Director> getAll() throws DAOException {
        return getAll(SELECT_ALL);
    }

    public Director getByName(String name) throws DAOException {
        return getByParameter(SELECT_FROM_DIRECTOR_WHERE_NAME, name).get(0);
    }
}
