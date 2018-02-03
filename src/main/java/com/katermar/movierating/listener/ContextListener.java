package com.katermar.movierating.listener;

import com.katermar.movierating.database.connection.ConnectionPool;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Created by katermar on 1/12/2018.
 */
public class ContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent event) {
        event.getServletContext();
        ConnectionPool.getInstance();
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        ConnectionPool.getInstance().close();
    }
}
