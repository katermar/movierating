package com.katermar.movierating.database.dao.impl;

import com.katermar.movierating.database.connection.ConnectionPool;
import com.katermar.movierating.database.dao.GenericDao;
import com.katermar.movierating.entity.Rating;
import com.katermar.movierating.exception.DAOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by katermar on 1/9/2018.
 */
public class RatingDaoImpl implements GenericDao<Rating> {
    private static final String SELECT_FROM_RATING_WHERE_IDUSER = "SELECT * FROM rating WHERE iduser = ?";
    private static final String SELECT_FROM_RATING_WHERE_IDFILM = "SELECT * FROM rating WHERE idfilm = ?";
    private static final String SELECT_AVG_RATING_AMOUNT_WHERE_IDFILM = "SELECT AVG(rating_amount) FROM rating WHERE idfilm = ?";
    private static final String SELECT_FROM_RATING_WHERE_IS_SEEN_0 = "SELECT * FROM rating WHERE is_seen = 0";

    public List<Rating> findByUser(long userId) throws DAOException {
        return findByParameter(String.valueOf(userId), SELECT_FROM_RATING_WHERE_IDFILM);
    }

    public Double findAverageByFilm(long filmId) throws DAOException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_AVG_RATING_AMOUNT_WHERE_IDFILM)) {
            statement.setLong(1, filmId);
            try (ResultSet selected = statement.executeQuery()) {
                selected.next();
                return selected.getDouble(1);
            }
        } catch (SQLException e) {
            throw new DAOException(e); // todo specific message
        }
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
