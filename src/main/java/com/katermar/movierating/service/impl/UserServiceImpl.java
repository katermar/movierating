package com.katermar.movierating.service.impl;

import com.katermar.movierating.database.dao.UserDao;
import com.katermar.movierating.database.dao.impl.UserDaoImpl;
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
            throw new ServiceException(e); // todo specific message
        }
    }

    public User getById(long userId) throws ServiceException {
        try {
            return userDAO.getById(userId);
        } catch (DAOException e) {
            throw new ServiceException(e); //todo
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
            throw new ServiceException(e); // todo specific message
        }
    }

    @Override
    public void updatePassword(String password, String login) throws ServiceException {
        AuthSecurityService authSecurityService = new AuthSecurityServiceImpl();
        User user = getByLogin(login);
        boolean executed;
        try {
            executed = userDAO.updatePassword(authSecurityService.hashPassword(password), user.getId());
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
//
//    boolean updateBan(User.UserStatus status, String login) throws ServiceException {
//        User user = getByLogin(login);
//        boolean executed = false;
//        try {
//            executed = userDAO.updateBan(status.toString().toLowerCase(), user.getId());
//        } catch (DAOException e) {
//            throw new ServiceException(e);
//        }
//        return executed;
//    }
}
