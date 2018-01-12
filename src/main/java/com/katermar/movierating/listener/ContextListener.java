package com.katermar.movierating.listener;

import com.katermar.movierating.database.connection.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Created by katermar on 1/12/2018.
 */
public class ContextListener implements ServletContextListener {
    private static final Logger logger = LogManager.getLogger(ServletContextListener.class);

    @Override
    public void contextInitialized(ServletContextEvent event) {
        ServletContext context = event.getServletContext();

//        SecurityManager.createInstance(context.getRealPath("") + context.getInitParameter("security-config-location"));
        ConnectionPool.getInstance();
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        ConnectionPool.getInstance().close();
    }
}
