package com.katermar.movierating.service.impl;

import com.katermar.movierating.database.dao.UserDao;
import com.katermar.movierating.database.dao.impl.UserDaoImpl;
import com.katermar.movierating.entity.User;
import com.katermar.movierating.exception.DAOException;
import com.katermar.movierating.exception.ServiceException;
import com.katermar.movierating.service.AdminService;
import com.katermar.movierating.service.UserService;

/**
 * Created by katermar on 1/10/2018.
 */
public class AdminServiceImpl implements AdminService {
    @Override
    public void updateBan(String login) throws ServiceException {
        UserService userService = new UserServiceImpl();
        try {
            User user = userService.getByLogin(login);
            UserDao userDao = new UserDaoImpl();
            userDao.updateStatus((User.UserStatus.BANED == user.getStatus() ? "unconfirmed" : "baned"), user.getId());
        } catch (ServiceException | DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }
}
