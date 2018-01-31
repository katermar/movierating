package com.katermar.movierating.config;

/**
 * Created by katermar on 1/6/2018.
 */
public class Parameter {
    public static final String RUSSIAN_LOCALE = "ru_RU";
    public static final String ENGLISH_LOCALE = "en_US";

    public static final String EMAIL_SERVER = "email.server";
    public static final String EMAIL_PASSWORD = "email.password";
    public static final String EMAIL_USERNAME = "email.username";
    public static final String EMAIL_AUTH_FROM = "email.auth.from";
    public static final String EMAIL_AUTH_SUBJECT = "email.auth.subject";
    public static final String EMAIL_AUTH_MESSAGE = "email.auth.message";
    public static final String EMAIL_PASSWORD_MESSAGE = "email.password.message";
    public static final String EMAIL_PASSWORD_SUBJECT = "email.password.message";

    public static final String USERNAME_REGEX = "^[a-zA-Z]([a-zA-Z0-9_]+){4,}";
    public static final String PASSWORD_REGEX = "(?=.{6,})(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9]).*";
    public static final String EMAIL_REGEX = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@" +
            "(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?";
}
