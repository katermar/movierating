package com.katermar.movierating.service;

import com.katermar.movierating.entity.Rating;
import com.katermar.movierating.exception.ServiceException;

import java.util.List;
import java.util.Map;

/**
 * Created by katermar on 2/3/2018.
 */
public interface RatingService {
    Double getAverageRatingByUser(int userId) throws ServiceException;

    List<Rating> getRatingListByUser(int userId) throws ServiceException;

    Map<String, Long> getRatingMapByUser(long userId) throws ServiceException;

    Double getAverageRatingByFilm(long filmId) throws ServiceException;

    void addRating(Rating rating) throws ServiceException;

    Rating getRatingByUserAndFilm(int userId, int filmId) throws ServiceException;
}
