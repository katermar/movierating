package com.katermar.movierating.service.impl;

import com.katermar.movierating.database.dao.impl.ReviewDaoImpl;
import com.katermar.movierating.entity.Review;
import com.katermar.movierating.exception.DAOException;
import com.katermar.movierating.exception.ServiceException;

import java.util.List;

/**
 * Created by katermar on 1/14/2018.
 */
public class ReviewService {
    private static final ReviewDaoImpl reviewDao = new ReviewDaoImpl();
    private static final UserServiceImpl userService = new UserServiceImpl();

    public List<Review> findByFilm(long filmId) throws ServiceException {
        try {
            List<Review> reviewList = reviewDao.findByFilm(filmId);
            for (Review review : reviewList) {
                review.setUser(userService.findById(review.getIduser()));
            }
            return reviewList;
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    public boolean addReview(Review review) throws ServiceException {
        try {
            return reviewDao.create(review);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
