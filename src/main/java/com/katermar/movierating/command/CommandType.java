package com.katermar.movierating.command;

import com.katermar.movierating.command.factory.CommandFactory;
import com.katermar.movierating.command.logic.AdminLogic;
import com.katermar.movierating.command.logic.GeneralLogic;
import com.katermar.movierating.command.logic.UserLogic;

/**
 * Created by katermar on 12/30/2017.
 * <p>
 * An Enum of commands which are identified with the
 *
 * @see CommandFactory class
 * Commands are split into groups to use in filters
 * Each command holds an instance of functional interface Command
 * @see Command
 */
public enum CommandType {
    // Common Commands
    MAIN_PAGE(new GeneralLogic()::showMainPage),
    RATING_PAGE(new GeneralLogic()::showRatingPage),
    FILMS_PAGE(new GeneralLogic()::showFilmsPage),
    FILM_INFO(new GeneralLogic()::showFilmInfoPage),
    REGISTER(new GeneralLogic()::register),
    SWITCH_LANGUAGE(new GeneralLogic()::switchLanguage),
    CHECK_LOGIN(new GeneralLogic()::checkLogin),
    SEARCH_FILMS(new GeneralLogic()::searchFilms),
    FORGOT_PASSWORD(new UserLogic()::forgotPassword),
    LOGIN(new UserLogic()::login),

    // Authorized user commands
    LOGOUT(new UserLogic()::logout),
    EDIT_PROFILE(new UserLogic()::editProfile),
    LEAVE_REVIEW(new UserLogic()::leaveReview),
    SET_AVATAR(new UserLogic()::setAvatar),
    ADD_RATING(new UserLogic()::addRating),
    WATCH(new UserLogic()::updateWatched),
    SEND_NEW_PASSWORD(new UserLogic()::sendNewPassword),
    CONFIRM_EMAIL(new UserLogic()::confirmEmail),
    SEND_EMAIL(new UserLogic()::sendEmail),
    PROFILE_PAGE(new UserLogic()::showProfilePage),

    // Admin commands
    USERS_PAGE(new AdminLogic()::showUsersPage),
    ADD_PAGE(new AdminLogic()::showAddPage),
    ADD_FILM(new AdminLogic()::addFilm),
    ADD_GENRE(new AdminLogic()::addGenre),
    ADD_DIRECTOR(new AdminLogic()::addDirector),
    BAN_USER(new AdminLogic()::banUser),
    DELETE_FILM(new AdminLogic()::deleteFilm);

    private final Command command;

    CommandType(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }
}
