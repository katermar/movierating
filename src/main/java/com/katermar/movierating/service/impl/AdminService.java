package com.katermar.movierating.service.impl;

import com.katermar.movierating.database.dao.UserDao;
import com.katermar.movierating.database.dao.impl.UserDaoImpl;
import com.katermar.movierating.entity.User;
import com.katermar.movierating.exception.DAOException;
import com.katermar.movierating.exception.ServiceException;
import com.katermar.movierating.service.UserService;

import java.sql.Date;
import java.time.LocalDate;

/**
 * Created by katermar on 1/10/2018.
 */
public class AdminService {
    public boolean updateBan(String date, String login) throws ServiceException {
        UserService userService = new UserServiceImpl();
        try {
            User user = userService.getByLogin(login);
            UserDao userDao = new UserDaoImpl();
            Date parsedDate = Date.valueOf(LocalDate.parse(date));
            userDao.updateBan(parsedDate, user.getId());
        } catch (ServiceException | DAOException e) {
            throw new ServiceException(e);
        }
        return true;
    }
}
