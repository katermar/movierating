package com.katermar.movierating.filter;

import com.katermar.movierating.command.CommandType;
import com.katermar.movierating.command.factory.CommandFactory;
import com.katermar.movierating.exception.ServiceException;
import com.katermar.movierating.util.ParametersValidator;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by katermar on 2/2/2018.
 *
 * Filter to validate parameters
 */
@WebFilter(filterName = "ValidationFilter", urlPatterns = "/controller")
public class ValidationFilter implements Filter {
    private Set<String> filmValidateCommands = new HashSet<>();
    private Set<String> userValidateCommands = new HashSet<>();

    /**
     * Creates sets of commands which have to be validated.
     *
     * @param filterConfig
     * @throws ServletException
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        filmValidateCommands = Set.of(CommandType.ADD_DIRECTOR.name(), CommandType.ADD_FILM.name(), CommandType.ADD_GENRE.name());
        userValidateCommands = Set.of(CommandType.REGISTER.name(), CommandType.EDIT_PROFILE.name(), CommandType.SEND_NEW_PASSWORD.name());
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        try {
            if (validate(request)) {
                filterChain.doFilter(servletRequest, servletResponse);
            } else {
                response.sendError(400, "Bad request parameters");
            }
        } catch (ServiceException e) {
            response.sendError(500, e.getMessage());
        }
    }

    /**
     * If command have to be validated, uses util validator class to validate request parameters.
     *
     * @param request
     * @return result of validation
     * @throws ServiceException
     */
    private boolean validate(HttpServletRequest request) throws ServiceException {
        String command = CommandFactory.revertIntoConstantName(request.getParameter("command"));
        HashMap<String, String[]> parametersMap = new HashMap<>(request.getParameterMap());
        if (filmValidateCommands.contains(command)) {
            return ParametersValidator.validateFilmParameters(parametersMap);
        } else if (userValidateCommands.contains(command)) {
            return ParametersValidator.validateUserParameters(parametersMap);
        }
        return true;
    }

    @Override
    public void destroy() {

    }
}
