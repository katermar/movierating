package com.katermar.movierating.config;

/**
 * Created by katermar on 1/4/2018.
 * <p>
 * Class of page paths constants
 */
public class PagePath {
    public static final String MAIN = "WEB-INF/jsp/main.jsp";
    public static final String PROFILE = "WEB-INF/jsp/profile.jsp";
    public static final String FILMS = "WEB-INF/jsp/films.jsp";
    public static final String FILM_INFO = "WEB-INF/jsp/film_info.jsp";
    public static final String ERROR = "WEB-INF/jsp/error.jsp";
    public static final String RATING = "WEB-INF/jsp/rating.jsp";
    public static final String USERS = "WEB-INF/jsp/users.jsp";
    public static final String ADD = "WEB-INF/jsp/admin_add.jsp";

    public static final String REDIRECT_MAIN = "/controller?command=main-page";
    public static final String REDIRECT_FILMS = "/controller?command=films-page";
}
