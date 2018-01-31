package com.katermar.movierating.service.impl;

import com.katermar.movierating.database.dao.impl.RatingDaoImpl;
import com.katermar.movierating.entity.Rating;
import com.katermar.movierating.exception.DAOException;
import com.katermar.movierating.exception.ServiceException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by katermar on 1/10/2018.
 */
public class RatingService {
    private static final RatingDaoImpl ratingDao = new RatingDaoImpl();

    public Double getAverageRatingByUser(int userId) throws ServiceException {
        try {
            return ratingDao.getByUser(userId)
                    .stream()
                    .mapToDouble(Rating::getRatingAmount)
                    .average()
                    .orElse(0.);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    public List<Rating> getRatingListByUser(int userId) throws ServiceException {
        try {
            return ratingDao.getByUser(userId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    public Map<String, Long> getRatingMapByUser(long userId) throws ServiceException {
        Map<String, Long> ratingMap = new HashMap<>();
        try {
            List<Rating> ratings = ratingDao.getByUser(userId);
            ratings.forEach(rating -> {
                String ratingAmount = String.valueOf(rating.getRatingAmount());
                ratingMap.put(ratingAmount, ratingMap.getOrDefault(ratingAmount, 0L) + 1);
            });
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return ratingMap;
    }

    public Double getAverageRatingByFilm(long filmId) throws ServiceException {
        try {
            return ratingDao.getAverageByFilm(filmId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    public void addRating(Rating rating) throws ServiceException {
        try {
            ratingDao.create(rating);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    public Rating getRatingByUserAndFilm(int userId, int filmId) throws ServiceException {
        try {
            return ratingDao.getByUserAndFilm(userId, filmId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

}
