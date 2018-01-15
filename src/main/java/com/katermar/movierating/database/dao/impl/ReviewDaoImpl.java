package com.katermar.movierating.database.dao.impl;

import com.katermar.movierating.database.connection.ConnectionPool;
import com.katermar.movierating.database.dao.GenericDao;
import com.katermar.movierating.entity.Review;
import com.katermar.movierating.exception.DAOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by katermar on 1/9/2018.
 */
public class ReviewDaoImpl implements GenericDao<Review> {
    private static final String SELECT_WHERE_IDUSER = "SELECT * FROM review WHERE iduser = ?";
    private static final String SELECT_WHERE_IDFILM = "SELECT * FROM review WHERE idfilm = ?";
    private static final String INSERT_IDUSER_IDFILM_TEXT_VALUES = "INSERT INTO review (iduser, idfilm, text) VALUES (?, ?, ?)";

    public List<Review> getByUser(int userId) throws DAOException {
        return getByParameter(SELECT_WHERE_IDUSER, String.valueOf(userId));
    }

    public List<Review> getByFilm(long filmId) throws DAOException {
        return getByParameter(SELECT_WHERE_IDFILM, String.valueOf(filmId));
    }

    @Override
    public Review constructFromResultSet(ResultSet selectedReviews) throws SQLException {
        Review review = new Review();
        review.setDate(selectedReviews.getTimestamp("date"));
        review.setIdfilm(selectedReviews.getInt("idfilm"));
        review.setIdreview(selectedReviews.getInt("idreview"));
        review.setIduser(selectedReviews.getInt("iduser"));
        review.setText(selectedReviews.getString("text"));
        return review;
    }

    @Override
    public Review deleteById(long id) {
        return null;
    }

    @Override
    public boolean create(Review review) throws DAOException {
        try (Connection connectionFromPool = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connectionFromPool.prepareStatement(INSERT_IDUSER_IDFILM_TEXT_VALUES)) {
            statement.setLong(1, review.getIduser());
            statement.setLong(2, review.getIdfilm());
            statement.setString(3, review.getText());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return true;
    }
}
