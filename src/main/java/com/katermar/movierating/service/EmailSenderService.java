package com.katermar.movierating.service;

import com.katermar.movierating.exception.ServiceException;

/**
 * Created by katermar on 1/8/2018.
 */
public interface EmailSenderService {
    void sendConfirmationMail(String username, String userEmail) throws ServiceException;

    void sendNewPasswordMail(String username, String userEmail) throws ServiceException;
}
