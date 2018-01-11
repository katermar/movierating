package com.katermar.movierating.service;

import com.katermar.movierating.entity.User;
import com.katermar.movierating.exception.ServiceException;

/**
 * Created by katermar on 1/8/2018.
 */
public interface UserService {
    User findByLoginAndPassword(String login, String password) throws ServiceException;

    User findByLogin(String login) throws ServiceException;

    boolean addUser(User user) throws ServiceException;

    boolean updatePassword(String password, String login) throws ServiceException;
}
