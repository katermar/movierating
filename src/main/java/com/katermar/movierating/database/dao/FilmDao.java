package com.katermar.movierating.database.dao;

import com.katermar.movierating.entity.Film;
import com.katermar.movierating.exception.DAOException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Created by katermar on 2/3/2018.
 */
public interface FilmDao extends GenericDao<Film> {
    List<Film> getByUser(long userId) throws DAOException;

    Film getById(long filmId) throws DAOException;

    List<Film> getAll(int pageNumber, int filmsPerPage) throws DAOException;

    List<Film> getOrderedByRatingDesc() throws DAOException;

    int getFilmsAmount() throws DAOException;

    void deleteById(String id) throws DAOException;

    @Override
    boolean create(Film film) throws DAOException;

    @Override
    Film constructFromResultSet(ResultSet resultSet) throws SQLException;

    List<Film> getFilmsByDirector(int directorId) throws DAOException;

    List<Film> getFilmsByGenre(String genreName) throws DAOException;

    Film getByName(String name) throws DAOException;

    List<Film> searchFilms(Map<String, String[]> parametersMap, int pageNumber, int filmsPerPage) throws DAOException;

    int searchFilmsAmount(Map<String, String[]> parametersMap) throws DAOException;

    default String constructSearchQuery(Map<String, String[]> parameters) {
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
