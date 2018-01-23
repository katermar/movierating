package com.katermar.movierating.service.impl;

import com.katermar.movierating.entity.User;
import com.katermar.movierating.exception.ServiceException;
import com.katermar.movierating.service.AuthSecurityService;
import com.katermar.movierating.service.EmailSenderService;
import com.katermar.movierating.service.RegisterService;


/**
 * Created by katermar on 1/6/2018.
 */
public class RegisterServiceImpl implements RegisterService {
    private static final UserServiceImpl userService = new UserServiceImpl();
    private static final AuthSecurityService authSecurityService = new AuthSecurityServiceImpl();
    private static final EmailSenderService emailSenderService = new EmailSenderServiceImpl();

    @Override
    public void register(User user) throws ServiceException {
        user.setPassword(authSecurityService.hashPassword(user.getPassword()));
        userService.addUser(user);
        emailSenderService.sendConfirmationMail(user.getLogin(), user.getEmail());
    }

    @Override
    public void confirmEmail(User user) throws ServiceException {
        userService.updateStatus(User.UserStatus.UNBANED, user.getLogin());
    }
}
