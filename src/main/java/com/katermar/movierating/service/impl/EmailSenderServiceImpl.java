package com.katermar.movierating.service.impl;

import com.katermar.movierating.config.Parameter;
import com.katermar.movierating.exception.ServiceException;
import com.katermar.movierating.service.EmailSenderService;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.jasypt.util.text.BasicTextEncryptor;

import java.util.ResourceBundle;

/**
 * Created by katermar on 1/7/2018.
 */
public class EmailSenderServiceImpl implements EmailSenderService {
    private static final ResourceBundle emailBundle = ResourceBundle.getBundle("project");

    @Override
    public boolean sendConfirmationMail(String username, String userEmail) throws ServiceException {
        BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
        textEncryptor.setPassword(ResourceBundle.getBundle("project").getString("encryption.password"));

        Email email = new SimpleEmail();
        email.setHostName(emailBundle.getString(Parameter.EMAIL_SERVER));
        email.setSmtpPort(465);
        email.setAuthenticator(new DefaultAuthenticator(
                emailBundle.getString(Parameter.EMAIL_USERNAME),
                emailBundle.getString(Parameter.EMAIL_PASSWORD))
        );
        email.setSSLOnConnect(true);
        try {
            email.setFrom("Movierating<" + emailBundle.getString(Parameter.EMAIL_AUTH_FROM) + ">");
            email.setSubject(emailBundle.getString(Parameter.EMAIL_AUTH_SUBJECT));
            email.setMsg(emailBundle.getString(Parameter.EMAIL_AUTH_MESSAGE) + textEncryptor.encrypt(username));
            email.addTo(userEmail);
            email.send();
        } catch (EmailException e) {
            throw new ServiceException(e);
        }
        return true;
    }

    @Override
    public boolean sendNewPasswordMail(String password, String userEmail) throws ServiceException {
        Email email = new SimpleEmail();
        email.setHostName(emailBundle.getString(Parameter.EMAIL_SERVER));
        email.setSmtpPort(465);
        email.setAuthenticator(new DefaultAuthenticator(
                emailBundle.getString(Parameter.EMAIL_USERNAME),
                emailBundle.getString(Parameter.EMAIL_PASSWORD))
        );
        email.setSSLOnConnect(true);
        try {
            email.setFrom("Movierating<" + emailBundle.getString(Parameter.EMAIL_AUTH_FROM) + ">");
            email.setSubject(emailBundle.getString(Parameter.EMAIL_PASSWORD_SUBJECT));
            email.setMsg(emailBundle.getString(Parameter.EMAIL_PASSWORD_MESSAGE) + password);
            email.addTo(userEmail);
            email.send();
        } catch (EmailException e) {
            throw new ServiceException(e);
        }
        return true;
    }
}
