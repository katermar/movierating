package com.katermar.movierating.database.dao.impl;

import com.katermar.movierating.database.connection.ConnectionPool;
import com.katermar.movierating.database.dao.GenericDao;
import com.katermar.movierating.entity.Rating;
import com.katermar.movierating.exception.DAOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by katermar on 1/9/2018.
 */
public class RatingDaoImpl implements GenericDao<Rating>{
    private static final String SELECT_FROM_RATING_WHERE_IDUSER = "SELECT * FROM rating WHERE iduser = ?";
    private static final String SELECT_FROM_RATING_WHERE_IS_SEEN_0 = "SELECT * FROM rating WHERE is_seen = 0";

    public List<Rating> findByUser(long userId) throws DAOException {
        return findByParameter(String.valueOf(userId), SELECT_FROM_RATING_WHERE_IDUSER);
    }

    public List<Rating> findByParameter(String param, String statementString) throws DAOException {
        List<Rating> ratings = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(statementString)) {
            statement.setString(1, param);
            try (ResultSet selected = statement.executeQuery()) {
                while (selected.next()) {
                    ratings.add(constructRatingFromResultSet(selected));
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e); // todo specific message
        }
        return ratings;
    }

    private Rating constructRatingFromResultSet(ResultSet selectedRatings) throws SQLException {
        Rating rating = new Rating();
        rating.setIsSeen(selectedRatings.getBoolean("is_seen"));
        rating.setIdfilm(selectedRatings.getInt("idfilm"));
        rating.setIdrating(selectedRatings.getInt("idrating"));
        rating.setIduser(selectedRatings.getInt("iduser"));
        rating.setRatingAmount(selectedRatings.getInt("rating_amount"));
        return rating;
    }

    @Override
    public Rating deleteById(long id) {
        return null;
    }

    @Override
    public Rating constructFromResultSet(ResultSet selectedUsers) throws SQLException {
        return null;
    }
}
