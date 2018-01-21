package com.katermar.movierating.service.impl;

import com.katermar.movierating.database.dao.impl.DirectorDaoImpl;
import com.katermar.movierating.entity.Director;
import com.katermar.movierating.exception.DAOException;
import com.katermar.movierating.exception.ServiceException;

import java.util.List;

/**
 * Created by katermar on 1/16/2018.
 */
public class DirectorServiceImpl {
    private static final DirectorDaoImpl DIRECTOR_DAO = new DirectorDaoImpl();

    public List<Director> getAll() throws ServiceException {
        try {
            return DIRECTOR_DAO.getAll();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    public Director getByName(String name) throws ServiceException {
        try {
            return DIRECTOR_DAO.getByName(name);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    public void addDirector(Director director) throws ServiceException {
        try {
            DIRECTOR_DAO.create(director);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
