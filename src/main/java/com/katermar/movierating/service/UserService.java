package com.katermar.movierating.service;

import com.katermar.movierating.entity.User;
import com.katermar.movierating.exception.ServiceException;

import java.util.Map;

/**
 * Created by katermar on 1/8/2018.
 */
public interface UserService {
    Map<User, Double> getUserRatingMap() throws ServiceException;

    User getByLoginAndPassword(String login, String password) throws ServiceException;

    User getByLogin(String login) throws ServiceException;

    boolean addUser(User user) throws ServiceException;

    boolean updatePassword(String password, String login) throws ServiceException;
}
