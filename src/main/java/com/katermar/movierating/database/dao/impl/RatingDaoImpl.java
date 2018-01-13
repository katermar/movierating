package com.katermar.movierating.database.dao.impl;

import com.katermar.movierating.database.dao.GenericDao;
import com.katermar.movierating.entity.Rating;
import com.katermar.movierating.exception.DAOException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by katermar on 1/9/2018.
 */
public class RatingDaoImpl implements GenericDao<Rating>{
    private static final String SELECT_FROM_RATING_WHERE_IDUSER = "SELECT * FROM rating WHERE iduser = ?";
    private static final String SELECT_FROM_RATING_WHERE_IDFILM = "SELECT * FROM rating WHERE idfilm = ?";
    private static final String SELECT_FROM_RATING_WHERE_IS_SEEN_0 = "SELECT * FROM rating WHERE is_seen = 0";

    public List<Rating> findByUser(long userId) throws DAOException {
        return findByParameter(String.valueOf(userId), SELECT_FROM_RATING_WHERE_IDFILM);
    }

    public List<Rating> findAverageFilm(long filmId) throws DAOException {
        return findByParameter(String.valueOf(filmId), SELECT_FROM_RATING_WHERE_IDUSER);
    }

    @Override
    public Rating deleteById(long id) {
        return null;
    }

    @Override
    public Rating constructFromResultSet(ResultSet selectedRatings) throws SQLException {
        Rating rating = new Rating();
        rating.setIsSeen(selectedRatings.getBoolean("is_seen"));
        rating.setIdfilm(selectedRatings.getInt("idfilm"));
        rating.setIdrating(selectedRatings.getInt("idrating"));
        rating.setIduser(selectedRatings.getInt("iduser"));
        rating.setRatingAmount(selectedRatings.getInt("rating_amount"));
        return rating;
    }
}
