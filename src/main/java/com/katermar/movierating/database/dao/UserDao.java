package com.katermar.movierating.database.dao;

import com.katermar.movierating.entity.User;
import com.katermar.movierating.exception.DAOException;

import java.util.List;

/**
 * Created by katermar on 1/8/2018.
 */
public interface UserDao extends GenericDao<User> {
    boolean create(User user) throws DAOException;

    User getById(long id) throws DAOException;

    List<User> getAll() throws DAOException;

    void updatePassword(String password, long userId) throws DAOException;

    boolean updateStatus(String status, long userId) throws DAOException;

    boolean updateRole(String role, long userId) throws DAOException;

    User getByLoginAndPassword(String login, String password) throws DAOException;

    User getByLogin(String login) throws DAOException;

    void updatePoints(User user) throws DAOException;
}
