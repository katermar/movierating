package com.katermar.movierating.service;

import com.katermar.movierating.entity.Rating;
import com.katermar.movierating.entity.User;
import com.katermar.movierating.exception.ServiceException;

import java.util.Map;

/**
 * Created by katermar on 1/8/2018.
 */
public interface UserService {
    Map<User, Double> getUserRatingMap() throws ServiceException;

    User getByLoginAndPassword(String login, String password) throws ServiceException;

    User getById(long userId) throws ServiceException;

    User getByLogin(String login) throws ServiceException;

    void addUser(User user) throws ServiceException;

    void updatePassword(String password, String login) throws ServiceException;

    boolean updateStatus(User.UserStatus status, String login) throws ServiceException;

    void updateLevel(User user, Rating userRating) throws ServiceException;
}
