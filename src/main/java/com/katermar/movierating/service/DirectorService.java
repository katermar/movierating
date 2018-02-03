package com.katermar.movierating.service;

import com.katermar.movierating.entity.Director;
import com.katermar.movierating.exception.ServiceException;

import java.util.List;

/**
 * Created by katermar on 2/3/2018.
 */
public interface DirectorService {
    List<Director> getAll() throws ServiceException;

    Director getByName(String name) throws ServiceException;

    void addDirector(Director director) throws ServiceException;
}
