package com.katermar.movierating.service;

import com.katermar.movierating.entity.User;
import com.katermar.movierating.exception.ServiceException;

/**
 * Created by katermar on 1/8/2018.
 */
public interface RegisterService {
    void register(User user) throws ServiceException;

    void confirmEmail(User user) throws ServiceException;
}
