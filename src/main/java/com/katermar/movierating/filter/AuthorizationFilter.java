package com.katermar.movierating.filter;

import com.katermar.movierating.command.CommandType;
import com.katermar.movierating.command.factory.CommandFactory;
import com.katermar.movierating.entity.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by katermar on 2/2/2018.
 * <p>
 * Filter to give access to some commands only to admin.
 */
public class AuthorizationFilter implements Filter {
    private Set<String> adminCommands = new HashSet<>();

    /**
     * Adds admin commands as string names from the enum set.
     *
     * @param filterConfig
     * @throws ServletException
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        EnumSet<CommandType> commandTypes = EnumSet.range(CommandType.USERS_PAGE, CommandType.DELETE_FILM);
        commandTypes.forEach(commandType -> adminCommands.add(commandType.name()));
    }


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpSession currentSession = request.getSession(false);
        if (currentSession == null || currentSession.getAttribute("user") == null) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            authorize(servletRequest, servletResponse, filterChain);
        }
    }

    private void authorize(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        User currentUser = (User) request.getSession().getAttribute("user");
        boolean isAdmin = currentUser.getRole() == User.UserRole.ADMIN;
        String command = CommandFactory.revertIntoConstantName(request.getParameter("command"));
        if (isAdmin || !adminCommands.contains(command)) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            response.sendError(400, "Bad request");
        }
    }

    @Override
    public void destroy() {
    }
}
