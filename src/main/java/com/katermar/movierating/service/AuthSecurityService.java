package com.katermar.movierating.service;

import com.katermar.movierating.entity.User;
import com.katermar.movierating.exception.ServiceException;

/**
 * Created by katermar on 1/8/2018.
 */
public interface AuthSecurityService {
    User checkUserCredentials(String login, String password) throws ServiceException;

    String hashPassword(String password);
}
