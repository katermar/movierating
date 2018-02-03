package com.katermar.movierating.database.dao;

import com.katermar.movierating.entity.Rating;
import com.katermar.movierating.exception.DAOException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by katermar on 2/3/2018.
 */
public interface RatingDao extends GenericDao<Rating> {
    List<Rating> getByUser(long userId) throws DAOException;

    Rating getByUserAndFilm(long userId, long filmId) throws DAOException;

    Double getAverageByFilm(long filmId) throws DAOException;

    @Override
    boolean create(Rating rating) throws DAOException;

    @Override
    Rating constructFromResultSet(ResultSet selectedRatings) throws SQLException;
}
