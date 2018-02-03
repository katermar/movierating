package com.katermar.movierating.service.impl;

import com.katermar.movierating.database.dao.ReviewDao;
import com.katermar.movierating.database.dao.impl.ReviewDaoImpl;
import com.katermar.movierating.entity.Review;
import com.katermar.movierating.exception.DAOException;
import com.katermar.movierating.exception.ServiceException;
import com.katermar.movierating.service.RatingService;
import com.katermar.movierating.service.ReviewService;
import com.katermar.movierating.service.UserService;

import java.util.List;

/**
 * Created by katermar on 1/14/2018.
 */
public class ReviewServiceImpl implements ReviewService {
    private static final ReviewDao REVIEW_DAO = new ReviewDaoImpl();
    private static final UserService USER_SERVICE = new UserServiceImpl();
    private static final RatingService RATING_SERVICE = new RatingServiceImpl();

    @Override
    public List<Review> getByFilm(long filmId) throws ServiceException {
        try {
            List<Review> reviewList = REVIEW_DAO.getByFilm(filmId);
            for (Review review : reviewList) {
                review.setUser(USER_SERVICE.getById(review.getIdUser()));
                review.setRating(RATING_SERVICE.getRatingByUserAndFilm(review.getIdUser(), review.getIdFilm()));
            }
            return reviewList;
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public void addReview(Review review) throws ServiceException {
        try {
            REVIEW_DAO.create(review);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }

}
