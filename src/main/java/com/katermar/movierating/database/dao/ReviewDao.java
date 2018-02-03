package com.katermar.movierating.database.dao;

import com.katermar.movierating.entity.Review;
import com.katermar.movierating.exception.DAOException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by katermar on 2/3/2018.
 */
public interface ReviewDao extends GenericDao<Review> {
    List<Review> getByUser(int userId) throws DAOException;

    List<Review> getByFilm(long filmId) throws DAOException;

    @Override
    Review constructFromResultSet(ResultSet selectedReviews) throws SQLException;

    @Override
    boolean create(Review review) throws DAOException;
}
