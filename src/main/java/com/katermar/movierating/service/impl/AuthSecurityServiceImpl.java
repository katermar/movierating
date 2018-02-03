package com.katermar.movierating.service.impl;

import com.katermar.movierating.entity.User;
import com.katermar.movierating.exception.ServiceException;
import com.katermar.movierating.service.AuthSecurityService;
import com.katermar.movierating.service.UserService;
import org.mindrot.jbcrypt.BCrypt;

/**
 * Created by katermar on 1/6/2018.
 */
public class AuthSecurityServiceImpl implements AuthSecurityService {
    private static final UserService USER_SERVICE = new UserServiceImpl();

    @Override
    public User checkUserCredentials(String login, String password) throws ServiceException {
        User user = USER_SERVICE.getByLogin(login);
        if (user.getLogin() != null && BCrypt.checkpw(password, user.getPassword())) {
            return user;
        }
        return null;
    }

    @Override
    public String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
}
