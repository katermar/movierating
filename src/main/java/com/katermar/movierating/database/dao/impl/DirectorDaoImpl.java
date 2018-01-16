package com.katermar.movierating.database.dao.impl;

import com.katermar.movierating.database.dao.GenericDao;
import com.katermar.movierating.entity.Director;
import com.katermar.movierating.exception.DAOException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by katermar on 1/15/2018.
 */
public class DirectorDaoImpl implements GenericDao<Director> {
    private static final String SELECT_ALL = "SELECT * FROM director";
    private static final String SELECT_DIRECTOR_BY_FILM = "SELECT * FROM director WHERE iddirector IN (SELECT iddirector FROM film WHERE idfilm = ?)";

    public Director getDirectorByFilm(int filmId) throws DAOException {
        return getByParameter(SELECT_DIRECTOR_BY_FILM, String.valueOf(filmId)).get(0);
    }

    @Override
    public Director deleteById(String id) {
        return null;
    }

    @Override
    public boolean create(Director user) throws DAOException {
        return false;
    }

    @Override
    public Director constructFromResultSet(ResultSet selected) throws SQLException {
        Director director = new Director();
        director.setName(selected.getString("name"));
        director.setIddirector(selected.getInt("iddirector"));
        return director;
    }

    public List<Director> getAll() throws DAOException {
        return getAll(SELECT_ALL);
    }
}
