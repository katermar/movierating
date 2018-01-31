package com.katermar.movierating.filter;


import com.katermar.movierating.command.CommandType;
import com.katermar.movierating.command.factory.CommandFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

public class AuthenticationFilter implements Filter {
    Set<String> grantCommands = new HashSet<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        EnumSet<CommandType> commandTypes = EnumSet.range(CommandType.MAIN_PAGE, CommandType.LOGIN);
        commandTypes.forEach(commandType -> grantCommands.add(commandType.name()));
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession currentSession = request.getSession(false);
        boolean loggedIn = currentSession != null && currentSession.getAttribute("user") != null;
        String command = CommandFactory.revertIntoConstantName(request.getParameter("command"));
        if (grantCommands.contains(command) || loggedIn) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            response.sendError(400, "Bad request");
        }

    }

    @Override
    public void destroy() {
    }
}
