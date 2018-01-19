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
    private static final String SELECT_FROM_FILM_WHERE_IDDIRECTOR = "SELECT * FROM film WHERE iddirector = ?";
    private static final String SELECT_FROM_FILM_WHERE_GENRE = "SELECT * FROM film WHERE idfilm IN (SELECT film_genre.idfilm FROM film_genre WHERE genrename = ?)";
    private static final String SELECT_WHERE_ID = "SELECT * FROM film WHERE idfilm = ?";
    private static final String SELECT_ALL_DESC = "select * from (SELECT film.* FROM rating\n" +
            "join film on rating.idfilm = film.idfilm and is_seen = '1' \n" +
            "group by idfilm order by AVG(rating_amount) desc) as w\n" +
            "union all select * from film where idfilm not in (SELECT film.idfilm FROM rating\n" +
            "join film on rating.idfilm = film.idfilm and is_seen = '1' \n" +
            "group by idfilm)";

    public List<Film> getByUser(long userId) throws DAOException {
        return getByParameter(SELECT_WHERE_RELEASE_YEAR, String.valueOf(userId));
    }

    public Film getById(long filmId) throws DAOException {
        return getByParameter(SELECT_WHERE_ID, String.valueOf(filmId)).get(0);
    }

    public List<Film> getAll() throws DAOException {
        return getAll(SELECT_ALL);
    }

    public List<Film> getOrderedByRatingDesc() throws DAOException {
        return getAll(SELECT_ALL_DESC);
    }

    @Override
    public Film deleteById(String id) {
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

    public List<Film> getFilmsByDirector(int directorId) throws DAOException {
        return getByParameter(SELECT_FROM_FILM_WHERE_IDDIRECTOR, String.valueOf(directorId));
    }

    public List<Film> getFilmsByGenre(String genreName) throws DAOException {
        return getByParameter(SELECT_FROM_FILM_WHERE_GENRE, genreName);
    }
}
