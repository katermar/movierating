package com.katermar.movierating.service.impl;

import com.katermar.movierating.database.dao.UserDao;
import com.katermar.movierating.database.dao.impl.UserDaoImpl;
import com.katermar.movierating.entity.Rating;
import com.katermar.movierating.entity.User;
import com.katermar.movierating.exception.DAOException;
import com.katermar.movierating.exception.ServiceException;
import com.katermar.movierating.service.AuthSecurityService;
import com.katermar.movierating.service.UserService;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by katermar on 1/1/2018.
 */
public class UserServiceImpl implements UserService {
    private static final UserDao userDAO = new UserDaoImpl();
    private static final RatingService RATING_SERVICE = new RatingService();

    @Override
    public Map<User, Double> getUserRatingMap() throws ServiceException {
        try {
            Map<User, Double> userRatingMap = new HashMap<>();
            for (User user : userDAO.getAll()) {
                userRatingMap.put(user, RATING_SERVICE.getAverageRatingByUser(user.getId()));
            }
            return userRatingMap;
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public User getByLoginAndPassword(String login, String password) throws ServiceException {
        try {
            return userDAO.getByLoginAndPassword(login, password);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    public User getById(long userId) throws ServiceException {
        try {
            return userDAO.getById(userId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public User getByLogin(String login) throws ServiceException {
        try {
            return userDAO.getByLogin(login);
        } catch (DAOException e) {
            return new User();
        }
    }

    @Override
    public void addUser(User user) throws ServiceException {
        try {
            userDAO.create(user);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void updatePassword(String password, String login) throws ServiceException {
        AuthSecurityService authSecurityService = new AuthSecurityServiceImpl();
        User user = getByLogin(login);
        try {
            userDAO.updatePassword(authSecurityService.hashPassword(password), user.getId());
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    boolean updateStatus(User.UserStatus status, String login) throws ServiceException {
        User user = getByLogin(login);
        boolean executed;
        try {
            executed = userDAO.updateStatus(status.toString().toLowerCase(), user.getId());
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return executed;
    }

    @Override
    public void updateLevel(User user, Rating userRating) throws ServiceException {
        Double averageRating = RATING_SERVICE.getAverageRatingByFilm(userRating.getIdfilm());
        Double ratingDifference = Math.abs(averageRating - userRating.getRatingAmount());
        if (ratingDifference == 0) {
            user.incrementLevelPoints(2);
        } else if (ratingDifference < 1) {
            user.incrementLevelPoints();
        } else if (ratingDifference < 2) {
            user.decrementLevelPoints();
        } else {
            user.decrementLevelPoints(2);
        }
        user.setLevel(User.UserLevel.getLevel(user.getLevelPoints()));
        try {
            userDAO.updatePoints(user);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
