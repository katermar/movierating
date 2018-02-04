package com.katermar.movierating.database.dao.impl;

import com.katermar.movierating.database.connection.ConnectionPool;
import com.katermar.movierating.database.dao.FilmDao;
import com.katermar.movierating.entity.Film;
import com.katermar.movierating.exception.DAOException;

import java.sql.*;
import java.util.List;
import java.util.Map;

/**
 * Created by katermar on 1/9/2018.
 *
 * Implementation of the interface, which is used to work with 'film' table.
 */
public class FilmDaoImpl implements FilmDao {
    private static final String SELECT_ALL = "SELECT * FROM film ORDER BY idfilm";
    private static final String DELETE_FROM_FILM_WHERE_IDFILM = "DELETE FROM film WHERE idfilm = ?";
    private static final String INSERT_NAME_RELEASE_YEAR_DURATION_POSTER_IDDIRECTOR_DESCRIPTION = "INSERT INTO film (name, release_year, duration, poster, iddirector, description) VALUES (?, ?, ?, ?, ?, ?) " +
            "ON DUPLICATE KEY UPDATE name = values(name), release_year = values(release_year), duration = values(duration), poster = values(poster), iddirector = values(iddirector), description = values(description)";
    private static final String SELECT_FROM_FILM_WHERE_NAME = "SELECT * FROM film WHERE name = ?";
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

    @Override
    public List<Film> getByUser(long userId) throws DAOException {
        return getByParameter(SELECT_WHERE_RELEASE_YEAR, String.valueOf(userId));
    }

    @Override
    public Film getById(long filmId) throws DAOException {
        return getByParameter(SELECT_WHERE_ID, String.valueOf(filmId)).get(0);
    }

    @Override
    public List<Film> getAll(int pageNumber, int filmsPerPage) throws DAOException {
        return getAll(SELECT_ALL + " LIMIT " + (pageNumber - 1) * filmsPerPage + ", " + filmsPerPage);
    }

    @Override
    public List<Film> getOrderedByRatingDesc() throws DAOException {
        return getAll(SELECT_ALL_DESC);
    }

    @Override
    public int getFilmsAmount() throws DAOException {
        return getAll(SELECT_ALL).size();
    }

    @Override
    public void deleteById(String id) throws DAOException {
        try (Connection connectionFromPool = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connectionFromPool
                     .prepareStatement(DELETE_FROM_FILM_WHERE_IDFILM)) {
            statement.setString(1, id);
            statement.execute();
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }

    @Override
    public boolean create(Film film) throws DAOException {
        try (Connection connectionFromPool = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connectionFromPool
                     .prepareStatement(INSERT_NAME_RELEASE_YEAR_DURATION_POSTER_IDDIRECTOR_DESCRIPTION)) {
            statement.setString(1, film.getName());
            statement.setInt(2, film.getReleaseYear());
            statement.setDouble(3, film.getDuration());
            statement.setString(4, film.getPoster());
            statement.setInt(5, film.getIdDirector());
            statement.setString(6, film.getDescription());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
        return true;
    }

    @Override
    public Film constructFromResultSet(ResultSet resultSet) throws SQLException {
        Film film = new Film();
        film.setName(resultSet.getString("name"));
        film.setReleaseYear(Date.valueOf(resultSet.getString("release_year")));
        film.setDuration(resultSet.getDouble("duration"));
        film.setIdDirector(resultSet.getInt("iddirector"));
        film.setId(resultSet.getInt("idfilm"));
        film.setPoster(resultSet.getString("poster"));
        film.setDescription(resultSet.getString("description"));
        return film;
    }

    @Override
    public List<Film> getFilmsByDirector(int directorId) throws DAOException {
        return getByParameter(SELECT_FROM_FILM_WHERE_IDDIRECTOR, String.valueOf(directorId));
    }

    @Override
    public List<Film> getFilmsByGenre(String genreName) throws DAOException {
        return getByParameter(SELECT_FROM_FILM_WHERE_GENRE, genreName);
    }

    @Override
    public Film getByName(String name) throws DAOException {
        try {
            return getByParameter(SELECT_FROM_FILM_WHERE_NAME, name).get(0);
        } catch (IndexOutOfBoundsException e) {
            return new Film();
        }
    }

    @Override
    public List<Film> searchFilms(Map<String, String[]> parametersMap, int pageNumber, int filmsPerPage) throws DAOException {
        return getAll(constructSearchQuery(parametersMap) + " LIMIT " + (pageNumber - 1) * filmsPerPage + ", " + filmsPerPage);
    }

    @Override
    public int searchFilmsAmount(Map<String, String[]> parametersMap) throws DAOException {
        return getAll(constructSearchQuery(parametersMap)).size();
    }

}
