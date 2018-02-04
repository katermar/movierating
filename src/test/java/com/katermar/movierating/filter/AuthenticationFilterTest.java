package com.katermar.movierating.filter;

import com.katermar.movierating.entity.User;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by katermar on 2/3/2018.
 */
public class AuthenticationFilterTest {
    @InjectMocks
    private AuthenticationFilter authenticationFilter;

    @Mock
    private HttpSession session;

    @Mock
    private FilterChain filterChain;

    @Mock
    private HttpServletRequest servletRequestNoSession;

    @Mock
    private HttpServletRequest servletRequest;

    @Mock
    private HttpServletResponse servletResponse;

    @Mock
    private FilterConfig filterConfig;

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testLoggedInDoFilter() throws Exception {
        when(servletRequest.getSession(false)).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(new User());
        when(servletRequest.getParameter("command")).thenReturn("profile-page");
        authenticationFilter.doFilter(servletRequest, servletResponse, filterChain);
        verify(filterChain).doFilter(servletRequest, servletResponse);
    }

    @Test
    public void testNonLoggedInDoFilter() throws Exception {
        when(servletRequestNoSession.getSession(false)).thenReturn(null);
        when(servletRequestNoSession.getParameter("command")).thenReturn("login");
        authenticationFilter.init(filterConfig);
        authenticationFilter.doFilter(servletRequestNoSession, servletResponse, filterChain);
        verify(filterChain).doFilter(servletRequestNoSession, servletResponse);
    }
}