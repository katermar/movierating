package com.katermar.movierating.command;

import com.katermar.movierating.command.logic.GeneralLogic;
import com.katermar.movierating.command.logic.UserLogic;

/**
 * Created by katermar on 12/30/2017.
 */
public enum CommandType {
    LOGIN(new UserLogic()::login),
    LOGOUT(new UserLogic()::logout),
    REGISTER(new GeneralLogic()::register),
    GO_TO_MAIN_PAGE(new GeneralLogic()::goToMainPage),
    GO_TO_PROFILE_PAGE(new UserLogic()::goToProfilePage),
    SWITCH_LANGUAGE(new GeneralLogic()::switchLanguage),
    UPDATE_PASSWORD(new UserLogic()::updatePassword),
    CONFIRM_EMAIL(new UserLogic()::confirmEmail);

    private Command command;

    CommandType(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }
}
