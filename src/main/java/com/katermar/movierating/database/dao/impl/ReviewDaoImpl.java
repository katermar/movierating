package com.katermar.movierating.database.dao.impl;

import com.katermar.movierating.database.connection.ConnectionPool;
import com.katermar.movierating.database.dao.GenericDao;
import com.katermar.movierating.entity.Review;
import com.katermar.movierating.entity.User;
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
public class ReviewDaoImpl implements GenericDao<Review>{
    private static final String SELECT_WHERE_IDUSER = "SELECT * FROM review WHERE iduser = ?";
    private static final String SELECT_WHERE_IDFILM = "SELECT * FROM review WHERE idfilm = ?";

    public List<Review> findByUser(int userId) throws DAOException {
        return findByParameter(String.valueOf(userId), SELECT_WHERE_IDUSER);
    }

    public List<Review> findByFilm(int filmId) throws DAOException {
        return findByParameter(String.valueOf(filmId), SELECT_WHERE_IDFILM);
    }

    public List<Review> findByParameter(String param, String statementString) throws DAOException {
        List<Review> reviews = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(statementString)) {
            statement.setString(1, param);
            try (ResultSet selected = statement.executeQuery()) {
                while (selected.next()) {
                    reviews.add(constructReviewFromResultSet(selected));
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e); // todo specific message
        }
        return reviews;
    }

    private Review constructReviewFromResultSet(ResultSet selectedReviews) throws SQLException {
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
    public Review constructFromResultSet(ResultSet selectedUsers) throws SQLException {
        return null;
    }
}
