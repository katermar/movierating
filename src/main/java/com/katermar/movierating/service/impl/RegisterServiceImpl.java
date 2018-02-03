package com.katermar.movierating.service.impl;

import com.katermar.movierating.entity.User;
import com.katermar.movierating.exception.ServiceException;
import com.katermar.movierating.service.AuthSecurityService;
import com.katermar.movierating.service.EmailSenderService;
import com.katermar.movierating.service.RegisterService;
import com.katermar.movierating.service.UserService;


/**
 * Created by katermar on 1/6/2018.
 */
public class RegisterServiceImpl implements RegisterService {
    private static final UserService USER_SERVICE = new UserServiceImpl();
    private static final AuthSecurityService AUTH_SECURITY_SERVICE = new AuthSecurityServiceImpl();
    private static final EmailSenderService EMAIL_SENDER_SERVICE = new EmailSenderServiceImpl();

    @Override
    public void register(User user) throws ServiceException {
        user.setPassword(AUTH_SECURITY_SERVICE.hashPassword(user.getPassword()));
        USER_SERVICE.addUser(user);
        EMAIL_SENDER_SERVICE.sendConfirmationMail(user.getLogin(), user.getEmail());
    }

    @Override
    public void confirmEmail(User user) throws ServiceException {
        USER_SERVICE.updateStatus(User.UserStatus.UNBANED, user.getLogin());
    }
}
