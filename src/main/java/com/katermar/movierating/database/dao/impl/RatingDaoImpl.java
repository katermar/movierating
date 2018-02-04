package com.katermar.movierating.database.dao.impl;

import com.katermar.movierating.database.connection.ConnectionPool;
import com.katermar.movierating.database.dao.RatingDao;
import com.katermar.movierating.entity.Rating;
import com.katermar.movierating.exception.DAOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by katermar on 1/9/2018.
 *
 * Implementation of the interface, which is used to work with 'rating' table.
 */
public class RatingDaoImpl implements RatingDao {
    private static final String SELECT_FROM_RATING_WHERE_IDUSER = "SELECT * FROM rating WHERE iduser = ? AND is_seen = '1'";
    private static final String SELECT_FROM_RATING_WHERE_IDUSER_AND_IDFILM = "SELECT * FROM rating WHERE (iduser = ? AND idfilm = ?)";
    private static final String SELECT_AVG_RATING_AMOUNT_WHERE_IDFILM = "SELECT AVG(rating_amount) FROM rating WHERE idfilm = ? AND is_seen = '1'";
    private static final String INSERT_IDUSER_IDFILM_IS_SEEN_RATING_AMOUNT_VALUES = "INSERT INTO rating (iduser, idfilm, is_seen, rating_amount) \n" +
            "VALUES (?, ?, ?, ?)\n" +
            " ON DUPLICATE KEY UPDATE \n" +
            " rating_amount=VALUES(rating_amount), is_seen=VALUES(is_seen)";

    @Override
    public List<Rating> getByUser(long userId) throws DAOException {
        return getByParameter(SELECT_FROM_RATING_WHERE_IDUSER, String.valueOf(userId));
    }

    @Override
    public Rating getByUserAndFilm(long userId, long filmId) throws DAOException {
        List<Rating> ratings = getByParameter(SELECT_FROM_RATING_WHERE_IDUSER_AND_IDFILM, String.valueOf(userId), String.valueOf(filmId));
        return ratings.isEmpty() ? new Rating() : ratings.get(0);
    }

    @Override
    public Double getAverageByFilm(long filmId) throws DAOException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_AVG_RATING_AMOUNT_WHERE_IDFILM)) {
            statement.setLong(1, filmId);
            try (ResultSet selected = statement.executeQuery()) {
                selected.next();
                return selected.getDouble(1);
            }
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }

    @Override
    public boolean create(Rating rating) throws DAOException {
        try (Connection connectionFromPool = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connectionFromPool.prepareStatement(INSERT_IDUSER_IDFILM_IS_SEEN_RATING_AMOUNT_VALUES)) {
            statement.setInt(1, rating.getIdUser());
            statement.setInt(2, rating.getIdFilm());
            statement.setBoolean(3, rating.getIsSeen());
            statement.setInt(4, rating.getRatingAmount());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
        return true;
    }

    @Override
    public Rating constructFromResultSet(ResultSet selectedRatings) throws SQLException {
        Rating rating = new Rating();
        rating.setIsSeen(selectedRatings.getBoolean("is_seen"));
        rating.setIdFilm(selectedRatings.getInt("idfilm"));
        rating.setId(selectedRatings.getInt("idrating"));
        rating.setIdUser(selectedRatings.getInt("iduser"));
        rating.setRatingAmount(selectedRatings.getInt("rating_amount"));
        return rating;
    }
}
