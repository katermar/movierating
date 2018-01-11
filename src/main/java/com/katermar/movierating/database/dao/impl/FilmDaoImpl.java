package com.katermar.movierating.database.dao.impl;

import com.katermar.movierating.database.dao.GenericDao;
import com.katermar.movierating.entity.Film;
import com.katermar.movierating.exception.DAOException;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by katermar on 1/9/2018.
 */
public class FilmDaoImpl implements GenericDao<Film> {
    private static final String SELECT_ALL = "SELECT * FROM film";
    private static final String SELECT_WHERE_RELEASE_YEAR = "SELECT * FROM film WHERE release_year = ?";

    public List<Film> findByUser(long userId) throws DAOException {
        return findByParameter(String.valueOf(userId), SELECT_WHERE_RELEASE_YEAR);
    }

    public List<Film> findAll() throws DAOException {
        return findAll(SELECT_ALL);
    }

    @Override
    public Film deleteById(long id) {
        return null;
    }

    @Override
    public Film constructFromResultSet(ResultSet resultSet) throws SQLException {
        Film film = new Film();
        film.setName(resultSet.getString("name"));
        film.setReleaseYear(Date.valueOf(resultSet.getString("release_date")));
        film.setDuration(resultSet.getDouble("duration"));
        film.setIddirector(resultSet.getInt("iddirector"));
        film.setIdfilm(resultSet.getInt("idfilm"));
        film.setPoster(resultSet.getString("poster"));
        return film;
    }
}
