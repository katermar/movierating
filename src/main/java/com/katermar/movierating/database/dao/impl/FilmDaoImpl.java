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
    private static final String SELECT_WHERE_ID = "SELECT * FROM film WHERE idfilm = ?";

    public List<Film> findByUser(long userId) throws DAOException {
        return findByParameter(SELECT_WHERE_RELEASE_YEAR, String.valueOf(userId));
    }

    public Film findById(long filmId) throws DAOException {
        return findByParameter(SELECT_WHERE_ID, String.valueOf(filmId)).get(0);
    }

    public List<Film> findAll() throws DAOException {
        return findAll(SELECT_ALL);
    }

    @Override
    public Film deleteById(long id) {
        return null;
    }

    @Override
    public boolean create(Film user) throws DAOException {
        return false; //todo
    }

    @Override
    public Film constructFromResultSet(ResultSet resultSet) throws SQLException {
        Film film = new Film();
        film.setName(resultSet.getString("name"));
        film.setReleaseYear(Date.valueOf(resultSet.getString("release_year")));
        film.setDuration(resultSet.getDouble("duration"));
        film.setIdDirector(resultSet.getInt("iddirector"));
        film.setIdFilm(resultSet.getInt("idfilm"));
        film.setPoster(resultSet.getString("poster"));
        film.setDescription(resultSet.getString("description"));
        return film;
    }
}
