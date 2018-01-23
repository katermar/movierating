package com.katermar.movierating.service.impl;

import com.katermar.movierating.database.dao.UserDao;
import com.katermar.movierating.database.dao.impl.UserDaoImpl;
import com.katermar.movierating.entity.User;
import com.katermar.movierating.exception.DAOException;
import com.katermar.movierating.exception.ServiceException;
import com.katermar.movierating.service.UserService;

/**
 * Created by katermar on 1/10/2018.
 */
public class AdminService {
    public void updateBan(String login) throws ServiceException {
        UserService userService = new UserServiceImpl();
        try {
            User user = userService.getByLogin(login);
            user.setStatus(User.UserStatus.BANED == user.getStatus() ? User.UserStatus.UNBANED : User.UserStatus.BANED);
            UserDao userDao = new UserDaoImpl();
            userDao.create(user);
        } catch (ServiceException | DAOException e) {
            throw new ServiceException(e);
        }
    }
}
