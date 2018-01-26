package com.katermar.movierating.command;

import com.katermar.movierating.command.logic.AdminLogic;
import com.katermar.movierating.command.logic.GeneralLogic;
import com.katermar.movierating.command.logic.UserLogic;

/**
 * Created by katermar on 12/30/2017.
 */
public enum CommandType {
    LOGIN(new UserLogic()::login),
    LOGOUT(new UserLogic()::logout),
    REGISTER(new GeneralLogic()::register),
    MAIN_PAGE(new GeneralLogic()::goToMainPage),
    ERROR_PAGE(new GeneralLogic()::goToErrorPage),
    RATING_PAGE(new GeneralLogic()::goToRatingPage),
    FILMS_PAGE(new GeneralLogic()::showFilmsPage),
    USERS_PAGE(new AdminLogic()::showUsersPage),
    ADD_PAGE(new AdminLogic()::showAddPage),
    ADD_FILM(new AdminLogic()::addFilm),
    ADD_GENRE(new AdminLogic()::addGenre),
    ADD_DIRECTOR(new AdminLogic()::addDirector),
    FILM_INFO(new GeneralLogic()::showFilmInfoPage),
    PROFILE_PAGE(new UserLogic()::goToProfilePage),
    SWITCH_LANGUAGE(new GeneralLogic()::switchLanguage),
    UPDATE_PASSWORD(new UserLogic()::updatePassword),
    CONFIRM_EMAIL(new UserLogic()::confirmEmail),
    SEND_EMAIL(new UserLogic()::sendEmail),
    ADD_RATING(new UserLogic()::addRating),
    WATCH(new UserLogic()::updateWatched),
    LEAVE_REVIEW(new UserLogic()::leaveReview),
    BAN_USER(new AdminLogic()::banUser),
    DELETE_FILM(new AdminLogic()::deleteFilm),
    EDIT_FILM(new AdminLogic()::editFilm),
    SET_AVATAR(new UserLogic()::setAvatar),
    CHECK_LOGIN(new GeneralLogic()::checkLogin),
    SEARCH_FILMS(new GeneralLogic()::searchFilms);

    private final Command command;

    CommandType(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }
}
