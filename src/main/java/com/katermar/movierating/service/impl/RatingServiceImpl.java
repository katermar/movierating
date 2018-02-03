package com.katermar.movierating.service.impl;

import com.katermar.movierating.database.dao.RatingDao;
import com.katermar.movierating.database.dao.impl.RatingDaoImpl;
import com.katermar.movierating.entity.Rating;
import com.katermar.movierating.exception.DAOException;
import com.katermar.movierating.exception.ServiceException;
import com.katermar.movierating.service.RatingService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by katermar on 1/10/2018.
 */
public class RatingServiceImpl implements RatingService {
    private static final RatingDao RATING_DAO = new RatingDaoImpl();

    @Override
    public Double getAverageRatingByUser(int userId) throws ServiceException {
        try {
            return RATING_DAO.getByUser(userId)
                    .stream()
                    .mapToDouble(Rating::getRatingAmount)
                    .average()
                    .orElse(0.);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public List<Rating> getRatingListByUser(int userId) throws ServiceException {
        try {
            return RATING_DAO.getByUser(userId);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public Map<String, Long> getRatingMapByUser(long userId) throws ServiceException {
        Map<String, Long> ratingMap = new HashMap<>();
        try {
            List<Rating> ratings = RATING_DAO.getByUser(userId);
            ratings.forEach(rating -> {
                String ratingAmount = String.valueOf(rating.getRatingAmount());
                ratingMap.put(ratingAmount, ratingMap.getOrDefault(ratingAmount, 0L) + 1);
            });
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
        return ratingMap;
    }

    @Override
    public Double getAverageRatingByFilm(long filmId) throws ServiceException {
        try {
            return RATING_DAO.getAverageByFilm(filmId);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public void addRating(Rating rating) throws ServiceException {
        try {
            RATING_DAO.create(rating);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public Rating getRatingByUserAndFilm(int userId, int filmId) throws ServiceException {
        try {
            return RATING_DAO.getByUserAndFilm(userId, filmId);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }

}
