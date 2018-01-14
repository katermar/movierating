package com.katermar.movierating.service.impl;

import com.katermar.movierating.database.dao.UserDao;
import com.katermar.movierating.database.dao.impl.UserDaoImpl;
import com.katermar.movierating.entity.User;
import com.katermar.movierating.exception.DAOException;
import com.katermar.movierating.exception.ServiceException;
import com.katermar.movierating.service.AuthSecurityService;
import com.katermar.movierating.service.UserService;

/**
 * Created by katermar on 1/1/2018.
 */
public class UserServiceImpl implements UserService {
    private static final UserDao userDAO = new UserDaoImpl();

    @Override
    public User findByLoginAndPassword(String login, String password) throws ServiceException {
        try {
            return userDAO.findByLoginAndPassword(login, password);
        } catch (DAOException e) {
            throw new ServiceException(e); // todo specific message
        }
    }

    public User findById(long userId) {
        return userDAO.findById(userId);
    }

    @Override
    public User findByLogin(String login) throws ServiceException {
        try {
            return userDAO.findByLogin(login);
        } catch (DAOException e) {
            throw new ServiceException(e); // todo specific message
        }
    }

    @Override
    public boolean addUser(User user) throws ServiceException {
        try {
            return userDAO.create(user);
        } catch (DAOException e) {
            throw new ServiceException(e); // todo specific message
        }
    }

    @Override
    public boolean updatePassword(String password, String login) throws ServiceException {
        AuthSecurityService authSecurityService = new AuthSecurityServiceImpl();
        User user = findByLogin(login);
        boolean executed = false;
        try {
            executed = userDAO.updatePassword(authSecurityService.hashPassword(password), user.getId());
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return executed;
    }

    boolean updateStatus(User.UserStatus status, String login) throws ServiceException {
        User user = findByLogin(login);
        boolean executed = false;
        try {
            executed = userDAO.updateStatus(status.toString().toLowerCase(), user.getId());
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return executed;
    }
//
//    boolean updateBan(User.UserStatus status, String login) throws ServiceException {
//        User user = findByLogin(login);
//        boolean executed = false;
//        try {
//            executed = userDAO.updateBan(status.toString().toLowerCase(), user.getId());
//        } catch (DAOException e) {
//            throw new ServiceException(e);
//        }
//        return executed;
//    }
}
