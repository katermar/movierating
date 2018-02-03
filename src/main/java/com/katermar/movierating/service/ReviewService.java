package com.katermar.movierating.service;

import com.katermar.movierating.entity.Review;
import com.katermar.movierating.exception.ServiceException;

import java.util.List;

/**
 * Created by katermar on 2/3/2018.
 */
public interface ReviewService {
    List<Review> getByFilm(long filmId) throws ServiceException;

    void addReview(Review review) throws ServiceException;
}
