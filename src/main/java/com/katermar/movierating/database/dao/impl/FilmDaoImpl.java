package com.katermar.movierating.database.dao.impl;

import com.katermar.movierating.database.connection.ConnectionPool;
import com.katermar.movierating.database.dao.GenericDao;
import com.katermar.movierating.entity.Film;
import com.katermar.movierating.exception.DAOException;

import java.sql.*;
import java.util.List;
import java.util.Map;

/**
 * Created by katermar on 1/9/2018.
 */
public class FilmDaoImpl implements GenericDao<Film> {
    private static final String SELECT_ALL = "SELECT * FROM film ORDER BY idfilm";
    private static final String DELETE_FROM_FILM_WHERE_IDFILM = "DELETE FROM film WHERE idfilm = ?";
    private static final String INSERT_NAME_RELEASE_YEAR_DURATION_POSTER_IDDIRECTOR_DESCRIPTION = "INSERT INTO film (name, release_year, duration, poster, iddirector, description) VALUES (?, ?, ?, ?, ?, ?)";
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

    public List<Film> getByUser(long userId) throws DAOException {
        return getByParameter(SELECT_WHERE_RELEASE_YEAR, String.valueOf(userId));
    }

    public Film getById(long filmId) throws DAOException {
        return getByParameter(SELECT_WHERE_ID, String.valueOf(filmId)).get(0);
    }

    public List<Film> getAll(int pageNumber, int filmsPerPage) throws DAOException {
        return getAll(SELECT_ALL + " LIMIT " + (pageNumber - 1) * filmsPerPage + ", " + filmsPerPage);
    }

    public List<Film> getOrderedByRatingDesc() throws DAOException {
        return getAll(SELECT_ALL_DESC);
    }

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
            throw new DAOException(e);
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
            throw new DAOException(e);
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

    public Film getByName(String name) throws DAOException {
        return getByParameter(SELECT_FROM_FILM_WHERE_NAME, name).get(0);
    }

    public List<Film> searchFilms(Map<String, String[]> parametersMap, int pageNumber, int filmsPerPage) throws DAOException {
        return getAll(constructSearchQuery(parametersMap) + " LIMIT " + (pageNumber - 1) * filmsPerPage + ", " + filmsPerPage);
    }

    public int searchFilmsAmount(Map<String, String[]> parametersMap) throws DAOException {
        return getAll(constructSearchQuery(parametersMap)).size();
    }

    private String constructSearchQuery(Map<String, String[]> parameters) {
        StringBuilder query = new StringBuilder("SELECT * FROM film WHERE ");
        if (parameters.containsKey("min-duration")) {
            query.append("duration >= ").append(parameters.get("min-duration")[0]).append(" AND ");
        } else {
            query.append("TRUE AND ");
        }
        if (parameters.containsKey("max-duration")) {
            query.append("duration <= ").append(parameters.get("max-duration")[0]).append(" AND ");
        } else {
            query.append("TRUE AND ");
        }
        if (parameters.containsKey("min-year")) {
            query.append("release_year >= ").append(parameters.get("min-year")[0]).append(" AND ");
        } else {
            query.append("TRUE AND ");
        }
        if (parameters.containsKey("max-year")) {
            query.append("release_year <= ").append(parameters.get("max-year")[0]).append(" AND ");
        } else {
            query.append("TRUE AND ");
        }
        if (parameters.containsKey("genre")) {
            for (String genre : parameters.get("genre")) {
                query
                        .append("idfilm IN (SELECT idfilm FROM film_genre WHERE genrename = '")
                        .append(genre).append("') AND ");
            }
        } else {
            query.append("TRUE AND ");
        }
        if (parameters.containsKey("director")) {
            query.append("iddirector IN (SELECT iddirector FROM director WHERE ");
            for (String director : parameters.get("director")) {
                query.append("name = '").append(director).append("' OR ");
            }
            query.append("FALSE ) AND ");
        }
        query.append("TRUE ORDER BY idfilm ");
        return query.toString();
    }
}
