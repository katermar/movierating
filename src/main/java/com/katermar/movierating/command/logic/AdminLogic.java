package com.katermar.movierating.command.logic;

import com.katermar.movierating.command.CommandResult;
import com.katermar.movierating.config.Attribute;
import com.katermar.movierating.config.PagePath;
import com.katermar.movierating.entity.User;
import com.katermar.movierating.exception.ServiceException;
import com.katermar.movierating.service.impl.AdminService;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by katermar on 1/9/2018.
 */
public class AdminLogic {
    public CommandResult banUser(HttpServletRequest request) {
        User currentUser = (User) request.getAttribute(Attribute.USER);
        String loginToBan = request.getParameter("loginToBan");
        String banTime = request.getParameter("banTime");
        AdminService adminService = new AdminService();
        if (currentUser.getRole() == User.UserRole.ADMIN) {
            try {
                adminService.updateBan(banTime, loginToBan);
            } catch (ServiceException e) {
                e.printStackTrace(); // todo
            }
        }
        return new CommandResult(CommandResult.ResponseType.REDIRECT, PagePath.PROFILE);
    }
}
