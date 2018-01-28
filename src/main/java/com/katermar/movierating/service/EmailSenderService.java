package com.katermar.movierating.service;

import com.katermar.movierating.exception.ServiceException;

/**
 * Created by katermar on 1/8/2018.
 */
public interface EmailSenderService {
    boolean sendConfirmationMail(String username, String userEmail) throws ServiceException;

    boolean sendNewPasswordMail(String username, String userEmail) throws ServiceException;
}
