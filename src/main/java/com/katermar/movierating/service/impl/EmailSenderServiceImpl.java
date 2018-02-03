package com.katermar.movierating.service.impl;

import com.katermar.movierating.config.Property;
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
    public void sendConfirmationMail(String username, String userEmail) throws ServiceException {
        BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
        textEncryptor.setPassword(ResourceBundle.getBundle("project").getString("encryption.password"));

        Email email = new SimpleEmail();
        email.setHostName(emailBundle.getString(Property.EMAIL_SERVER));
        email.setSmtpPort(465);
        email.setAuthenticator(new DefaultAuthenticator(
                emailBundle.getString(Property.EMAIL_USERNAME),
                emailBundle.getString(Property.EMAIL_PASSWORD))
        );
        email.setSSLOnConnect(true);
        try {
            email.setFrom("Movierating<" + emailBundle.getString(Property.EMAIL_AUTH_FROM) + ">");
            email.setSubject(emailBundle.getString(Property.EMAIL_AUTH_SUBJECT));
            email.setMsg(emailBundle.getString(Property.EMAIL_AUTH_MESSAGE) + textEncryptor.encrypt(username));
            email.addTo(userEmail);
            email.send();
        } catch (EmailException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public void sendNewPasswordMail(String password, String userEmail) throws ServiceException {
        Email email = new SimpleEmail();
        email.setHostName(emailBundle.getString(Property.EMAIL_SERVER));
        email.setSmtpPort(465);
        email.setAuthenticator(new DefaultAuthenticator(
                emailBundle.getString(Property.EMAIL_USERNAME),
                emailBundle.getString(Property.EMAIL_PASSWORD))
        );
        email.setSSLOnConnect(true);
        try {
            email.setFrom("Movierating<" + emailBundle.getString(Property.EMAIL_AUTH_FROM) + ">");
            email.setSubject(emailBundle.getString(Property.EMAIL_PASSWORD_SUBJECT));
            email.setMsg(emailBundle.getString(Property.EMAIL_PASSWORD_MESSAGE) + password);
            email.addTo(userEmail);
            email.send();
        } catch (EmailException e) {
            throw new ServiceException(e.getMessage());
        }
    }
}
