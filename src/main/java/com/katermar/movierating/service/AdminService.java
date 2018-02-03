package com.katermar.movierating.service;

import com.katermar.movierating.exception.ServiceException;

/**
 * Created by katermar on 2/3/2018.
 */
public interface AdminService {
    void updateBan(String login) throws ServiceException;
}
